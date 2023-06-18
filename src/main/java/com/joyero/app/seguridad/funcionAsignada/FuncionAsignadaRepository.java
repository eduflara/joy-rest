package com.joyero.app.seguridad.funcionAsignada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface FuncionAsignadaRepository extends JpaRepository<FuncionAsignada, Long>, QuerydslPredicateExecutor<FuncionAsignada> {

    @Query("select funcionAsignada from Grupo as grupo inner join grupo.funcionesAsginadas as funcionAsignada where grupo.id = :idGrupo")
    List<FuncionAsignada> findByGrupo(@Param("idGrupo") long idGrupo);

}
