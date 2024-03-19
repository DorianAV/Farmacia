package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.DTO.MedicamentoDTO;
import com.utsem.farmacia.Service.MedicamentoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("medicamento")
public class MedicamentoController {

    @Autowired
    MedicamentoService medicamentoService;

    @PostMapping("listar")
    public List<MedicamentoDTO> listar(HttpSession session) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.listar();		}
        return null;
    }
    @PostMapping("caducados")
    public List<LoteDTO> listarCaducados(HttpSession session){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return medicamentoService.listarCaducados();		}
        return null;
    }
    @PostMapping("registro")
    public String Registro(@RequestBody MedicamentoDTO medicamentoDTO) {
        System.out.println(medicamentoDTO.getFabricanteDTO().getUuid());

        return medicamentoService.registro(medicamentoDTO);
    }
}
