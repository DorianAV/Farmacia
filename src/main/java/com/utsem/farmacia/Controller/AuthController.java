package com.utsem.farmacia.Controller;

import com.utsem.farmacia.DTO.LoginDTO;
import com.utsem.farmacia.DTO.RespuestaDTO;
import com.utsem.farmacia.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("login")
    public RespuestaDTO Acceso(HttpSession session, @RequestBody LoginDTO datos) {
        return usuarioService.acceso(session, datos);
    }
    @PostMapping("estatus")
    public RespuestaDTO estatus(HttpSession session) {
        return session.getAttribute("estatus")!= null?
                (RespuestaDTO)session.getAttribute("estatus"):
                new RespuestaDTO();
    }
}
