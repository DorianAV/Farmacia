package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findByUsuarioAndContraseña(String usuario, String contraseña);

    Optional<Usuario> findByUsuario(String usuario);
}
