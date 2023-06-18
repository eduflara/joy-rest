package com.joyero.app.seguridad.sistema;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSistema is a Querydsl query type for Sistema
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSistema extends EntityPathBase<Sistema> {

    private static final long serialVersionUID = 263662936L;

    public static final QSistema sistema = new QSistema("sistema");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public final StringPath url = createString("url");

    public QSistema(String variable) {
        super(Sistema.class, forVariable(variable));
    }

    public QSistema(Path<? extends Sistema> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSistema(PathMetadata metadata) {
        super(Sistema.class, metadata);
    }

}

