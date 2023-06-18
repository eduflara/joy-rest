/*
 * Copyright (c) 2019.
 * Grupo.java
 * 13/06/17 9:09
 * alejandro
 */

package com.joyero.app.seguridad.grupo;

import com.joyero.app.seguridad.funcionAsignada.FuncionAsignada;
import com.joyero.base.Entidad;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * @author Alejandro
 */
@Entity
@Table(name = "SEG_GRUPO")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo implements Entidad {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @EqualsAndHashCode.Include
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false, length = 30)
    @NotBlank
    private String descripcion;

    @Column(name = "TIEMPO_SESION", nullable = false, length = 30)
    private Integer tiempoSesion;

    @Column(name = "ROOT")
    private Boolean root;

    //    @JsonIgnore
    @OneToMany(mappedBy = "grupo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FuncionAsignada> funcionesAsginadas;
}
