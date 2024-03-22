package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.FabricanteDTO;
import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Lote;
import com.utsem.farmacia.Model.Medicamento;
import com.utsem.farmacia.Repository.LoteRepository;
import com.utsem.farmacia.Repository.MedicamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoteService {
    @Autowired
    LoteRepository loteRepository;
    @Autowired
    MedicamentoRepository medicamentoRepository;
    @Autowired
    ModelMapper mapper;

    public String registro(LoteDTO loteDTO) {
        Lote lote = loteRepository.findByLote(loteDTO.getLote());
        if (lote == null) {
            try {
                Lote lote1 = mapper.map(loteDTO, Lote.class);
                Medicamento med = medicamentoRepository.findByCodigoDeBarras(loteDTO.getMedicamento().getCodigoDeBarras());
                System.out.println(loteDTO.getMedicamento().getCodigoDeBarras());
                lote1.setMedicamento(med);
                loteRepository.save(lote1);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        } else {
            try {
                lote.setExistencia(lote.getExistencia() + loteDTO.getExistencia());
                loteRepository.save(lote);
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        }
        return "Hubo un error al realizar el registro";
    }

    public List<LoteDTO> listar() {
        return loteRepository.findAll()
                .stream()
                .map(lote -> {
                    LoteDTO loteDTO = mapper.map(lote, LoteDTO.class);
                    loteDTO.setMedicamento(mapper.map(lote.getMedicamento(), MedicamentoDTO.class));
                    loteDTO.getMedicamento().setFabricanteDTO(mapper.map(lote.getMedicamento().getFabricante(), FabricanteDTO.class));
                    return loteDTO;
                })
                .collect(Collectors.toList());
    }
}
