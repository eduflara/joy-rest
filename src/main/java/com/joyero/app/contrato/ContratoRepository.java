package com.joyero.app.contrato;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long>, QuerydslPredicateExecutor<Contrato> {

}