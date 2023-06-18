/*
 * Copyright (c) 2019.
 * Sistema.java
 * 11/12/17 12:10
 * alejandro
 */

package com.joyero.app.seguridad.sistema;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by alejandro on 12/02/2016.
 */

@Entity
@Table(name = "SEG_SISTEMA")
@Data
public class Sistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SequenceIdGenerator")
    @SequenceGenerator(name = "SequenceIdGenerator", sequenceName = "id_seg_sistema")
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "NOMBRE", length = 50)
    private String nombre;

    @Column(name = "URL", length = 50)
    private String url;

}