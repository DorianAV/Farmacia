package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.FabricanteDTO;
import com.utsem.farmacia.DTO.FiltroMedicametoDTO;
import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Medicamento;
import com.utsem.farmacia.Repository.FabricanteRepository;
import com.utsem.farmacia.Repository.LoteRepository;
import com.utsem.farmacia.Repository.MedicamentoRepository;
import com.utsem.farmacia.Specification.MedicamentoSpecification;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {
    @Autowired
    MedicamentoRepository medicamentoRepository;
    @Autowired
    FabricanteRepository fabricanteRepository;
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    ModelMapper mapper;

    public List<MedicamentoDTO> listar() {
        return medicamentoRepository.findAll()
                .stream()
                .map(medicamento -> mapper.map(medicamento, MedicamentoDTO.class))
                .collect(Collectors.toList());
    }

    public List<MedicamentoDTO> filtrar(FiltroMedicametoDTO filtros) {
        return medicamentoRepository.findAll(new MedicamentoSpecification(filtros))
                .stream()
                .map(medicamento -> {
                    MedicamentoDTO medicamentoDTO= mapper.map(medicamento, MedicamentoDTO.class);
                    medicamentoDTO.setFabricanteDTO(mapper.map(medicamento.getFabricante(), FabricanteDTO.class));
                    return medicamentoDTO;
                }
                )
                .collect(Collectors.toList());
    }

    public List<LoteDTO> listarCaducados() {
        Date fechaActual = new Date();
        return loteRepository.findAll()
                .stream()
                .filter(lote -> lote.getFechaCaducidad() != null && lote.getFechaCaducidad().before(fechaActual))
                .map(lote -> {
                    if (lote != null && lote.getMedicamento() != null) {
                        LoteDTO loteDTO = mapper.map(lote, LoteDTO.class);
                        loteDTO.setMedicamento(mapper.map(lote.getMedicamento(), MedicamentoDTO.class));
                        loteDTO.getMedicamento().setFabricanteDTO(mapper.map(lote.getMedicamento().getFabricante(), FabricanteDTO.class));
                        return loteDTO;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<LoteDTO> listarProximosCaducar() {
        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Calcular la fecha dentro de 15 días
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DATE, 15);
        Date fechaCaducidadLimite = calendar.getTime();

        return loteRepository.findAll()
                .stream()
                .filter(lote -> lote.getFechaCaducidad() != null &&
                        lote.getFechaCaducidad().after(fechaActual) &&
                        lote.getFechaCaducidad().before(fechaCaducidadLimite))
                .map(lote -> {
                    if (lote != null && lote.getMedicamento() != null) {
                        LoteDTO loteDTO = mapper.map(lote, LoteDTO.class);
                        loteDTO.setMedicamento(mapper.map(lote.getMedicamento(), MedicamentoDTO.class));
                        loteDTO.getMedicamento().setFabricanteDTO(mapper.map(lote.getMedicamento().getFabricante(), FabricanteDTO.class));
                        return loteDTO;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public String registro(MedicamentoDTO medicamentoDTO) {
        Optional<Medicamento> med = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());
        Optional<Fabricante> fab = fabricanteRepository.findByUuid(medicamentoDTO.getFabricanteDTO().getUuid());
        if (med.isEmpty() && fab.isPresent()) {
            try {
                Medicamento medicamento = mapper.map(medicamentoDTO, Medicamento.class);
                medicamento.setFabricante(fab.get());
                medicamento.setEstatus(true);
                medicamentoRepository.save(medicamento);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        } else return "Ya hay un fabricante con ese nombre";
    }

    public MedicamentoDTO obtener(MedicamentoDTO medicamentoDTO){
        Optional<Medicamento> medicamento = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());
        MedicamentoDTO medDto= mapper.map(medicamento, MedicamentoDTO.class);
        return medDto;
    }
    public String actualizar(MedicamentoDTO medicamentoDTO) {
        Optional<Medicamento> medicamentoOptional = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());

        if (medicamentoOptional.isPresent()) {
            Medicamento med = medicamentoOptional.get();
            System.out.println(medicamentoDTO.getCodigoDeBarras());
            System.out.println(med.getCodigoDeBarras());
            med.setNombre(medicamentoDTO.getNombre());
            System.out.println(medicamentoDTO.getNombre());
            med.setPrecio(medicamentoDTO.getPrecio());
            med.setDosis(medicamentoDTO.getDosis());
            med.setEstatus(medicamentoDTO.isEstatus());
            medicamentoRepository.save(med);
            return "Actualizado";
        } else {
            return "No se encontró el medicamento para actualizar";
        }
    }



}
