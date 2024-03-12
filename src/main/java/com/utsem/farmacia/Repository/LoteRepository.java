package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    Lote findByLote(String Lote);

}
