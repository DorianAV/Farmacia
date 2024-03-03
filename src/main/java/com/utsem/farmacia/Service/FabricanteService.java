package com.utsem.farmacia.Service;

import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Model.Usuario;
import com.utsem.farmacia.Repository.FabricanteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                fabricanteRepository.save(fabricante);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        }
        else return "Ya hay un fabricante con ese nombre";
    }
}
