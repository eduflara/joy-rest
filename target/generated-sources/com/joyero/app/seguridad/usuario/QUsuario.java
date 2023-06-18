package com.joyero.app.seguridad.usuario;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsuario is a Querydsl query type for Usuario
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUsuario extends EntityPathBase<Usuario> {

    private static final long serialVersionUID = 907326320L;

    public static final QUsuario usuario = new QUsuario("usuario");

    public final BooleanPath activo = createBoolean("activo");

    public final BooleanPath bloqueado = createBoolean("bloqueado");

    public final StringPath correo = createString("correo");

    public final DateTimePath<java.util.Date> fechaAlta = createDateTime("fechaAlta", java.util.Date.class);

    public final DateTimePath<java.util.Date> fechaBaja = createDateTime("fechaBaja", java.util.Date.class);

    public final NumberPath<Long> funcionInicial = createNumber("funcionInicial", Long.class);

    public final SetPath<com.joyero.app.seguridad.grupo.Grupo, com.joyero.app.seguridad.grupo.QGrupo> grupos = this.<com.joyero.app.seguridad.grupo.Grupo, com.joyero.app.seguridad.grupo.QGrupo>createSet("grupos", com.joyero.app.seguridad.grupo.Grupo.class, com.joyero.app.seguridad.grupo.QGrupo.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idioma = createString("idioma");

    public final StringPath nombreCompleto = createString("nombreCompleto");

    public final StringPath password = createString("password");

    public final BooleanPath root = createBoolean("root");

    public final StringPath telefono = createString("telefono");

    public final StringPath username = createString("username");

    public QUsuario(String variable) {
        super(Usuario.class, forVariable(variable));
    }

    public QUsuario(Path<? extends Usuario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsuario(PathMetadata metadata) {
        super(Usuario.class, metadata);
    }

}

