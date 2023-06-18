package com.joyero.app.seguridad.registroAcceso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface RegistroAccesoRepository extends JpaRepository<RegistroAcceso, Long>, QuerydslPredicateExecutor<RegistroAcceso> {


}
