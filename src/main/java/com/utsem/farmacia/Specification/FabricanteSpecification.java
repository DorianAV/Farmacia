package com.utsem.farmacia.Specification;

import com.utsem.farmacia.DTO.FiltroDTO;
import com.utsem.farmacia.Model.Fabricante;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FabricanteSpecification implements Specification<Fabricante> {

    private FiltroDTO filtro;
    @Override
    public Predicate toPredicate(Root<Fabricante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (!filtro.getTexto().isEmpty()) {
            String textoFiltro = "%" + filtro.getTexto() + "%";
            Predicate nombrePredicate = criteriaBuilder.like(root.get("nombre"), textoFiltro);
            Predicate telefonoPredicate = criteriaBuilder.like(root.get("telefono"), textoFiltro);
            predicates.add(criteriaBuilder.or(nombrePredicate, telefonoPredicate));
        }



        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        return criteriaBuilder.and(predicatesArray);
    }
}
