package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Detalle_ventas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<Detalle_ventas, Long> {
}
