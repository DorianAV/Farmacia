package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.FabricanteDTO;
import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Model.Fabricante;
import com.utsem.farmacia.Service.FabricanteService;
import com.utsem.farmacia.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fabricante")
public class FabricanteController {
    @Autowired
    FabricanteService fabricanteService;

    @PostMapping("registro")
    public String Registro(HttpSession session,@RequestBody Fabricante fabricante) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {

            return fabricanteService.registro(fabricante);
        } return null;

    }
    @PostMapping("listar")
    public List<FabricanteDTO> listar(HttpSession session) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return fabricanteService.listar();		}
        return null;
    }
    @PostMapping("obtener")
    public FabricanteDTO obtener(HttpSession session, @RequestBody FabricanteDTO fabricanteDTO) {
        if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Administrador")) {

            return fabricanteService.obtener(fabricanteDTO);
        } else return null;
    }
    @PostMapping("actualizar")
    public String actualizar(HttpSession session, @RequestBody FabricanteDTO fabricanteDTO) {
        if (session.getAttribute("rol") != null && session.getAttribute("rol").equals("Administrador")) {

            return fabricanteService.actualizar(fabricanteDTO);
        } else return null;
    }
}
