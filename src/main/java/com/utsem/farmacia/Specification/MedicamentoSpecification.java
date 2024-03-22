package com.utsem.farmacia.Specification;

import com.utsem.farmacia.DTO.FiltroMedicametoDTO;
import com.utsem.farmacia.Model.Medicamento;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoSpecification implements Specification<Medicamento> {
    private FiltroMedicametoDTO filtro;

    public MedicamentoSpecification(FiltroMedicametoDTO filtro) {
        super();
        this.filtro = filtro;
    }

    private static final long serialVersionUID = 1L;
    @Override
    public Predicate toPredicate(Root<Medicamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesFinal = new ArrayList<>();

        if (filtro.getTexto() != null && !filtro.getTexto().isEmpty()) {
            String searchText = "%" + filtro.getTexto() + "%";
            Predicate nombrePredicate = criteriaBuilder.like(root.get("nombre"), searchText);
            Predicate sustanciaPredicate = criteriaBuilder.like(root.get("sustancia_activa"), searchText);
            Predicate fabricantePredicate = criteriaBuilder.like(root.get("fabricante").get("nombre"), searchText);

            Predicate combinedPredicate = criteriaBuilder.or(nombrePredicate, sustanciaPredicate, fabricantePredicate);
            predicatesFinal.add(combinedPredicate);
        }

        return criteriaBuilder.and(predicatesFinal.toArray(new Predicate[0]));
    }
}
