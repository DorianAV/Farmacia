package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    Lote findByLote(String Lote);
    Optional<Lote> findFirstByFechaCaducidadAfterOrderByFechaCaducidadAsc(Date fecha);
    List<Lote> findByFechaCaducidadAfterOrderByFechaCaducidadAsc(Date fechaMinima);

    @Query("SELECT l FROM Lote l WHERE l.medicamento.codigoDeBarras = :codigoDeBarras ORDER BY l.fechaCaducidad DESC")
    List<Lote> findAllByCodigoDeBarrasOrderByFechaCaducidadDesc(
            @Param("codigoDeBarras") int codigoDeBarras
    );

}