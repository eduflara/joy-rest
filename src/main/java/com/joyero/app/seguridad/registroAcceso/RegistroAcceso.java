/*
 * Copyright (c) 2019.
 * RegistroAcceso.java
 * 13/12/18 9:48
 * alejandro
 */

package com.joyero.app.seguridad.registroAcceso;

import com.joyero.app.seguridad.usuario.Usuario;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Alejandro
 */
@Entity
@Table(name = "SEG_REGISTRO_ACCESO")
@Data
public class RegistroAcceso {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "IP")
    private String ip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO", updatable = false, insertable = false)
    private Usuario usuario;

    @Column(name = "USUARIO")
    private Long idUsuario;

    @Column(name = "CIERRE_SESION")
    private Boolean cierreSesion;

    @Column(name = "url")
    private String url;

    @Column(name = "session_id")
    private String sessionId;

}