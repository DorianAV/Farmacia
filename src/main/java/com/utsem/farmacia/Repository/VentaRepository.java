package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Medicamento;
import com.utsem.farmacia.Model.Ventas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VentaRepository extends JpaRepository<Ventas, Long> {
}
