package com.joyero.app.seguridad.grupo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGrupo is a Querydsl query type for Grupo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGrupo extends EntityPathBase<Grupo> {

    private static final long serialVersionUID = 517043878L;

    public static final QGrupo grupo = new QGrupo("grupo");

    public final StringPath descripcion = createString("descripcion");

    public final SetPath<com.joyero.app.seguridad.funcionAsignada.FuncionAsignada, com.joyero.app.seguridad.funcionAsignada.QFuncionAsignada> funcionesAsginadas = this.<com.joyero.app.seguridad.funcionAsignada.FuncionAsignada, com.joyero.app.seguridad.funcionAsignada.QFuncionAsignada>createSet("funcionesAsginadas", com.joyero.app.seguridad.funcionAsignada.FuncionAsignada.class, com.joyero.app.seguridad.funcionAsignada.QFuncionAsignada.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public final BooleanPath root = createBoolean("root");

    public final NumberPath<Integer> tiempoSesion = createNumber("tiempoSesion", Integer.class);

    public QGrupo(String variable) {
        super(Grupo.class, forVariable(variable));
    }

    public QGrupo(Path<? extends Grupo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGrupo(PathMetadata metadata) {
        super(Grupo.class, metadata);
    }

}

