package com.joyero.app.seguridad.grupo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>, QuerydslPredicateExecutor<Grupo> {


    Grupo findByNombre(String nombre);

    @Query("select grupo from Usuario as usuario inner join usuario.grupos as grupo where usuario.id = :idUsuario")
    List<Grupo> findByUsuario(@Param("idUsuario") long idUsuario);

}
