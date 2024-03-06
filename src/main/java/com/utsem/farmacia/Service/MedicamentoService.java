package com.utsem.farmacia.Service;


import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Medicamento;
import com.utsem.farmacia.Repository.FabricanteRepository;
import com.utsem.farmacia.Repository.MedicamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {
    @Autowired
    MedicamentoRepository medicamentoRepository;
    @Autowired
    FabricanteRepository fabricanteRepository;
    @Autowired
    ModelMapper mapper;

    public List<MedicamentoDTO> listar() {
        return medicamentoRepository.findAll()
                .stream()
                .map(medicamento -> mapper.map(medicamento, MedicamentoDTO.class))
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
