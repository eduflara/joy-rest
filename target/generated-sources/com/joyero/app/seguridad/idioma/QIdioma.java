package com.joyero.app.seguridad.idioma;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdioma is a Querydsl query type for Idioma
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QIdioma extends EntityPathBase<Idioma> {

    private static final long serialVersionUID = 1250524112L;

    public static final QIdioma idioma = new QIdioma("idioma");

    public final StringPath codigoIdioma = createString("codigoIdioma");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nombre = createString("nombre");

    public QIdioma(String variable) {
        super(Idioma.class, forVariable(variable));
    }

    public QIdioma(Path<? extends Idioma> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdioma(PathMetadata metadata) {
        super(Idioma.class, metadata);
    }

}

