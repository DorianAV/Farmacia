package com.utsem.farmacia.Controller;

import com.utsem.farmacia.DTO.LoteDTO;
import com.utsem.farmacia.Service.LoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("lote")
public class LoteController {
    @Autowired
    LoteService loteService;

    @PostMapping("registro")
    public String Registro(HttpSession session,@RequestBody LoteDTO lote) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
        return loteService.registro(lote);} return null;
    }

    @PostMapping("listar")
    public List<LoteDTO> listar(HttpSession session) {
        if (session.getAttribute("rol") != null && (session.getAttribute("rol").equals("Administrador")|| session.getAttribute("rol").equals("Empleado"))) {
            return loteService.listar();
        }
        return null;
    }

}
