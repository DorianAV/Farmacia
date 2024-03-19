package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Medicamento;
import com.utsem.farmacia.Repository.FabricanteRepository;
import com.utsem.farmacia.Repository.LoteRepository;
import com.utsem.farmacia.Repository.MedicamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public List<LoteDTO> listarCaducados() {
        Date fechaActual = new Date(); // Fecha actual
        System.out.println(loteRepository.findAll());
        return loteRepository.findAll()
                .stream()
                .filter(lote -> lote.getFecha_caducidad() != null && lote.getFecha_caducidad().before(fechaActual)) // Filtrar los lotes caducados
                .map(lote -> {
                    if (lote != null && lote.getMedicamento() != null) {
                        LoteDTO loteDTO = mapper.map(lote, LoteDTO.class);
                        loteDTO.setMedicamento(mapper.map(lote.getMedicamento(), MedicamentoDTO.class));
                        return loteDTO;
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public String registro(MedicamentoDTO medicamentoDTO) {
        Medicamento med = medicamentoRepository.findByCodigoDeBarras(medicamentoDTO.getCodigoDeBarras());
        Optional<Fabricante> fab = fabricanteRepository.findByUuid(medicamentoDTO.getFabricanteDTO().getUuid());
        if (med==null && fab.isPresent()) {
            try {
                Medicamento medicamento = mapper.map(medicamentoDTO, Medicamento.class);
                medicamento.setFabricante(fab.get());
                medicamentoRepository.save(medicamento);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        } else return "Ya hay un fabricante con ese nombre";
    }

}
