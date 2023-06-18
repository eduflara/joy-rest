package com.joyero.app.seguridad.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, QuerydslPredicateExecutor<Usuario> {


    @Query("select distinct usuario from Usuario usuario "
            + "inner join fetch usuario.grupos as grupo "
            + "inner join fetch grupo.funcionesAsginadas as funcionAsignada "
            + "inner join fetch funcionAsignada.funcion "
            + "where usuario.username = :username")
    Usuario findByUsername(@Param("username") String username);

}
