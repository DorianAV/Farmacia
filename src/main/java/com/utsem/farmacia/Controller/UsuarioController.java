package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.LoginDTO;
import com.utsem.farmacia.DTO.RespuestaDTO;
import com.utsem.farmacia.DTO.UsuarioDTO;
import com.utsem.farmacia.Model.Usuario;
import com.utsem.farmacia.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @PostMapping("registro")
    public String Registro(HttpSession session,@RequestBody Usuario usuario){
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {

            return usuarioService.registro(usuario);} return null;
    }
    @PostMapping("listar")
    public List<UsuarioDTO> Registro(HttpSession session) {
        if(session.getAttribute("rol")!=null && session.getAttribute("rol").equals("Administrador")) {
            return usuarioService.listar();		}
        return null;    }

}
