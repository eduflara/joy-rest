package com.joyero.app.seguridad.funcion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface FuncionRepository extends JpaRepository<Funcion, Long>, QuerydslPredicateExecutor<Funcion> {

    List<Funcion> getAllByFuncionSuperiorIsNullAndApareceMenuIsTrue();

    List<Funcion> getAllByIdFuncionSuperior(Long idFuncionSuperior);

}
