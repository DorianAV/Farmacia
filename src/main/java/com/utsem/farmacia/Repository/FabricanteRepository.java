package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface FabricanteRepository extends JpaRepository<Fabricante, Long>, JpaSpecificationExecutor<Fabricante> {
    Optional<Fabricante> findByNombre(String nombre);
    Optional<Fabricante> findByUuid(UUID uuid);

}
