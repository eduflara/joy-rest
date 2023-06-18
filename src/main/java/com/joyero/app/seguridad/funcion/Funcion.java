/*
 * Copyright (c) 2019.
 * funcion.java
 * 13/12/18 9:45
 * alejandro
 */

package com.joyero.app.seguridad.funcion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joyero.app.seguridad.sistema.Sistema;
import com.joyero.base.Entidad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Alejandro
 */
@Entity
@Table(name = "seg_funcion")
@Data
public class Funcion implements Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long id;

    @Column(name = "icono", length = 20)
    private String icono;

    @Column(name = "url", length = 300)
    private String url;

    @Column(name = "id_pagina", length = 100)
    private String idPagina;

    @Column(name = "aparece_menu")
    private Boolean apareceMenu;

    @Column(name = "orden", precision = 3)
    private Integer orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sistema", updatable = false, insertable = false)
    private Sistema sistema;

    @Column(name = "sistema")
    private Long idSistema;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcion_superior", updatable = false, insertable = false)
    private Funcion funcionSuperior;

    @Column(name = "funcion_superior")
    private Long idFuncionSuperior;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "funcionSuperior")
    @OrderBy("orden")
    private Set<Funcion> funcionesInferiores;


}
