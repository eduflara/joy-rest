/*
 * Copyright (c) 2019.
 * Idioma.java
 * 13/06/17 9:09
 * alejandro
 */

package com.joyero.app.seguridad.idioma;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Alejandro
 */
@Entity
@Table(name = "SI_IDIOMA")
@Data
public class Idioma {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO_IDIOMA")
    private String codigoIdioma;

    @Column(name = "NOMBRE")
    private String nombre;

}