/*
 * Copyright (c) 2019.
 * FuncionAsignada.java
 * 13/02/19 15:42
 * alejandro
 */

package com.joyero.app.seguridad.funcionAsignada;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joyero.app.seguridad.funcion.Funcion;
import com.joyero.app.seguridad.grupo.Grupo;
import com.joyero.base.Entidad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "SEG_FUNCION_ASIGNADA")
@Data
public class FuncionAsignada implements Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FUNCION", updatable = false, insertable = false)
    private Funcion funcion;

    @Column(name = "funcion")
    private Long idFuncion;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GRUPO", updatable = false, insertable = false)
    private Grupo grupo;

    @Column(name = "GRUPO")
    private Long idGrupo;

    @Column(name = "alta")
    private Boolean alta;

    @Column(name = "baja")
    private Boolean baja;

    @Column(name = "modificar")
    private Boolean modificar;

    @Column(name = "consultar")
    private Boolean consultar;
}
