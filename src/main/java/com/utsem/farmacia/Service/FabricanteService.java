package com.utsem.farmacia.Service;

import com.utsem.farmacia.DTO.FabricanteDTO;
import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Lote;
import com.utsem.farmacia.Repository.FabricanteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FabricanteService {
    @Autowired
    FabricanteRepository fabricanteRepository;
    @Autowired
    ModelMapper mapper;

    public String registro(Fabricante fabricante){
        Optional<Fabricante> username = fabricanteRepository.findByNombre(fabricante.getNombre());
        if (username.isEmpty()){
            try {
                fabricante.setEstatus(true);
                fabricanteRepository.save(fabricante);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        }
        else return "Ya hay un fabricante con ese nombre";
    }

    public List<FabricanteDTO> listar() {
        return fabricanteRepository.findAll()
                .stream()
                .map(fabricante->mapper.map(fabricante, FabricanteDTO.class))
                .collect(Collectors.toList());
    }

    public FabricanteDTO obtener(FabricanteDTO fabricanteDTO) {
        Optional<Fabricante> fabricante = fabricanteRepository.findByUuid(fabricanteDTO.getUuid());

        FabricanteDTO fabricanteDTO1 = mapper.map(fabricante, FabricanteDTO.class);
        return fabricanteDTO1;
    }
    public String actualizar(FabricanteDTO fabricanteDTO) {
        Optional<Fabricante> fabricante = fabricanteRepository.findByUuid(fabricanteDTO.getUuid());

        if (fabricante.isPresent()){
            fabricante.get().setEstatus(fabricanteDTO.isEstatus());
            fabricante.get().setNombre(fabricanteDTO.getNombre());
            fabricante.get().setTelefono(fabricanteDTO.getTelefono());
            fabricanteRepository.save(fabricante.get());
            return "Actualizado";
        }
        else return "Hubo un error";

    }
}
