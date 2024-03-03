package com.utsem.farmacia.Controller;


import com.utsem.farmacia.DTO.LoginDTO;
import com.utsem.farmacia.DTO.RespuestaDTO;
import com.utsem.farmacia.Model.Usuario;
import com.utsem.farmacia.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @PostMapping("registro")
    public String Registro(@RequestBody Usuario usuario){
        return usuarioService.registro(usuario);
    }

}
