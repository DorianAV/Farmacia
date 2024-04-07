package com.utsem.farmacia.Service;

import com.utsem.farmacia.DTO.*;
import com.utsem.farmacia.Model.Lote;
import com.utsem.farmacia.Model.Usuario;
import com.utsem.farmacia.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        System.out.println(datos.getUsuario());
        System.out.println(datos.getContraseña());
        Optional<Usuario> usuario = usuarioRepository.findByUsuarioAndContraseña(datos.getUsuario(), datos.getContraseña());
        if (usuario.isEmpty()) {
            res.setRespuesta("Acceso Denegado");
            return res;
        } else {
            UsuarioDTO usuariosDTO = mapper.map(usuario.get(), UsuarioDTO.class);
            if (usuariosDTO.isEstatus()) {
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
            } else {
                res.setRespuesta("Acceso Denegado");
                return res;
            }

        }

    }

    public String registro(Usuario usuario) {
        Optional<Usuario> username = usuarioRepository.findByUsuario(usuario.getUsuario());
        if (username.isEmpty()) {
            try {
                usuario.setEstatus(true);
                usuarioRepository.save(usuario);
                return "Registro exitoso";
            } catch (Exception e) {
                return "Hubo un error al realizar el registro";
            }
        } else return "Ya hay una cuenta con ese usuario";
    }

    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> mapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO obtener(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());


        return mapper.map(usuario, UsuarioDTO.class);
    }

    public String actualizar(UsuarioUpdateDTO usuarioDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioDTO.getUsuario());
        if (usuario.isPresent()) {
            usuario.get().setNombre(usuarioDTO.getNombre());
            usuario.get().setApellido_pat(usuarioDTO.getApellido_pat());
            usuario.get().setApellido_mat(usuarioDTO.getApellido_mat());
            usuario.get().setCorreo(usuarioDTO.getCorreo());
            System.out.println("Contra"+ usuarioDTO.getContraseña());
            System.out.println();
            if (!Objects.equals(usuarioDTO.getContraseña(), "")) {
                usuario.get().setContraseña(usuarioDTO.getContraseña());
            }
            usuario.get().setTelefono(usuarioDTO.getTelefono());
            usuario.get().setRol(usuarioDTO.getRol());
            usuario.get().setEstatus(usuarioDTO.isEstatus());

            usuarioRepository.save(usuario.get());
            return "Actualizado";
        } else return "Hubo un error";

    }


}
