package com.joyero.app.seguridad.registroAcceso;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegistroAcceso is a Querydsl query type for RegistroAcceso
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRegistroAcceso extends EntityPathBase<RegistroAcceso> {

    private static final long serialVersionUID = -2123710704L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegistroAcceso registroAcceso = new QRegistroAcceso("registroAcceso");

    public final BooleanPath cierreSesion = createBoolean("cierreSesion");

    public final DateTimePath<java.util.Date> fecha = createDateTime("fecha", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> idUsuario = createNumber("idUsuario", Long.class);

    public final StringPath ip = createString("ip");

    public final StringPath sessionId = createString("sessionId");

    public final StringPath url = createString("url");

    public final com.joyero.app.seguridad.usuario.QUsuario usuario;

    public QRegistroAcceso(String variable) {
        this(RegistroAcceso.class, forVariable(variable), INITS);
    }

    public QRegistroAcceso(Path<? extends RegistroAcceso> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegistroAcceso(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegistroAcceso(PathMetadata metadata, PathInits inits) {
        this(RegistroAcceso.class, metadata, inits);
    }

    public QRegistroAcceso(Class<? extends RegistroAcceso> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usuario = inits.isInitialized("usuario") ? new com.joyero.app.seguridad.usuario.QUsuario(forProperty("usuario")) : null;
    }

}

