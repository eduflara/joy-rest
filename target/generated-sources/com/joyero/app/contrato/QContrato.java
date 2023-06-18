package com.joyero.app.contrato;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContrato is a Querydsl query type for Contrato
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QContrato extends EntityPathBase<Contrato> {

    private static final long serialVersionUID = 31755182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContrato contrato = new QContrato("contrato");

    public final com.joyero.app.QCliente cliente;

    public final NumberPath<Long> clienteId = createNumber("clienteId", Long.class);

    public final StringPath codigo = createString("codigo");

    public final DateTimePath<java.time.LocalDateTime> fechaAlta = createDateTime("fechaAlta", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lugartasacion = createString("lugartasacion");

    public final EnumPath<Mercado> mercado = createEnum("mercado", Mercado.class);

    public final StringPath personafirma = createString("personafirma");

    public final StringPath tasador = createString("tasador");

    public final EnumPath<TipoContrato> tipoContrato = createEnum("tipoContrato", TipoContrato.class);

    public QContrato(String variable) {
        this(Contrato.class, forVariable(variable), INITS);
    }

    public QContrato(Path<? extends Contrato> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContrato(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContrato(PathMetadata metadata, PathInits inits) {
        this(Contrato.class, metadata, inits);
    }

    public QContrato(Class<? extends Contrato> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cliente = inits.isInitialized("cliente") ? new com.joyero.app.QCliente(forProperty("cliente")) : null;
    }

}

