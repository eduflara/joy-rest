package com.joyero.app.seguridad.funcionAsignada;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFuncionAsignada is a Querydsl query type for FuncionAsignada
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFuncionAsignada extends EntityPathBase<FuncionAsignada> {

    private static final long serialVersionUID = 1395959644L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFuncionAsignada funcionAsignada = new QFuncionAsignada("funcionAsignada");

    public final BooleanPath alta = createBoolean("alta");

    public final BooleanPath baja = createBoolean("baja");

    public final BooleanPath consultar = createBoolean("consultar");

    public final com.joyero.app.seguridad.funcion.QFuncion funcion;

    public final com.joyero.app.seguridad.grupo.QGrupo grupo;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> idFuncion = createNumber("idFuncion", Long.class);

    public final NumberPath<Long> idGrupo = createNumber("idGrupo", Long.class);

    public final BooleanPath modificar = createBoolean("modificar");

    public QFuncionAsignada(String variable) {
        this(FuncionAsignada.class, forVariable(variable), INITS);
    }

    public QFuncionAsignada(Path<? extends FuncionAsignada> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFuncionAsignada(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFuncionAsignada(PathMetadata metadata, PathInits inits) {
        this(FuncionAsignada.class, metadata, inits);
    }

    public QFuncionAsignada(Class<? extends FuncionAsignada> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.funcion = inits.isInitialized("funcion") ? new com.joyero.app.seguridad.funcion.QFuncion(forProperty("funcion"), inits.get("funcion")) : null;
        this.grupo = inits.isInitialized("grupo") ? new com.joyero.app.seguridad.grupo.QGrupo(forProperty("grupo")) : null;
    }

}

