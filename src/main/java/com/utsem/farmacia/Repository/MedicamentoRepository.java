package com.utsem.farmacia.Repository;

import com.utsem.farmacia.Model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long>, JpaSpecificationExecutor<Medicamento> {

//    Optional<Medicamento> findByCodigo_de_barras(int codigo);
}
