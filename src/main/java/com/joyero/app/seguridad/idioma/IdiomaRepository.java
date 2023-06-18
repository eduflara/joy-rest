package com.joyero.app.seguridad.idioma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long>, QuerydslPredicateExecutor<Idioma> {


}
