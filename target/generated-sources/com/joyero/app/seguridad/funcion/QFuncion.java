package com.joyero.app.seguridad.funcion;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFuncion is a Querydsl query type for Funcion
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFuncion extends EntityPathBase<Funcion> {

    private static final long serialVersionUID = 314012828L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFuncion funcion = new QFuncion("funcion");

    public final BooleanPath apareceMenu = createBoolean("apareceMenu");

    public final SetPath<Funcion, QFuncion> funcionesInferiores = this.<Funcion, QFuncion>createSet("funcionesInferiores", Funcion.class, QFuncion.class, PathInits.DIRECT2);

    public final QFuncion funcionSuperior;

    public final StringPath icono = createString("icono");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> idFuncionSuperior = createNumber("idFuncionSuperior", Long.class);

    public final StringPath idPagina = createString("idPagina");

    public final NumberPath<Long> idSistema = createNumber("idSistema", Long.class);

    public final NumberPath<Integer> orden = createNumber("orden", Integer.class);

    public final com.joyero.app.seguridad.sistema.QSistema sistema;

    public final StringPath url = createString("url");

    public QFuncion(String variable) {
        this(Funcion.class, forVariable(variable), INITS);
    }

    public QFuncion(Path<? extends Funcion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFuncion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFuncion(PathMetadata metadata, PathInits inits) {
        this(Funcion.class, metadata, inits);
    }

    public QFuncion(Class<? extends Funcion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.funcionSuperior = inits.isInitialized("funcionSuperior") ? new QFuncion(forProperty("funcionSuperior"), inits.get("funcionSuperior")) : null;
        this.sistema = inits.isInitialized("sistema") ? new com.joyero.app.seguridad.sistema.QSistema(forProperty("sistema")) : null;
    }

}

