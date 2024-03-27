package com.utsem.farmacia.Service;

import com.utsem.farmacia.DTO.FabricanteDTO;
import com.utsem.farmacia.DTO.LoginDTO;
import com.utsem.farmacia.DTO.UsuarioDTO;
import com.utsem.farmacia.DTO.RespuestaDTO;
import com.utsem.farmacia.Model.Usuario;
import com.utsem.farmacia.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ModelMapper mapper;

    public RespuestaDTO acceso(HttpSession session, LoginDTO datos) {
        RespuestaDTO res = new RespuestaDTO();
        Optional<Usuario> usuario = usuarioRepository.findByUsuarioAndContraseña(datos.getUsuario(), datos.getContraseña());
        if (usuario.isEmpty()) {
            res.setRespuesta("Acceso Denegado");
            return res;
        } else {
            UsuarioDTO usuariosDTO = mapper.map(usuario.get(), UsuarioDTO.class);
            if (usuariosDTO.getRol().equals("Administrador")) {
                res.setRuta("admin.html");
            }

            if (usuariosDTO.getRol().equals("Empleado")) {
                res.setRuta("venta.html");
            }
            res.setAcceso(true);
            res.setRespuesta("Acceso Correcto");
            session.setAttribute("credenciales", usuariosDTO);
            session.setAttribute("estatus", res);
            session.setAttribute("rol", usuariosDTO.getRol());
            session.setAttribute("usuario", usuariosDTO.getUsuario());
            return res;
        }
    }

    public String registro(Usuario usuario) {
        Optional<Usuario> username = usuarioRepository.findByUsuario(usuario.getUsuario());
        if (username.isEmpty()){
            try {
                usuario.setEstatus(true);
                usuarioRepository.save(usuario);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        }
        else return "Ya hay una cuenta con ese usuario";
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario->mapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }


}
