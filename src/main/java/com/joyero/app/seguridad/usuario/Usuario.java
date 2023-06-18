/*
 * Copyright (c) 2019.
 * Usuario.java
 * 20/12/18 12:44
 * alejandro
 */

package com.joyero.app.seguridad.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joyero.app.seguridad.funcionAsignada.FuncionAsignada;
import com.joyero.app.seguridad.grupo.Grupo;
import com.joyero.base.Entidad;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author Alejandro
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SEG_USUARIO")
@Data
public class Usuario implements UserDetails, Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, length = 30)
    @NotBlank
    @NotNull
    private String username;

    @Column(name = "clave", nullable = false, length = 40)
    @NotBlank
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @Column(name = "nombre_persona", nullable = false, length = 60)
    @NotBlank
    private String nombreCompleto;

    //con access = JsonProperty.Access.WRITE_ONLY
    //no exporta "READ" la propiedad para generar el JSON
    //pero la escribe "WRITE" cuando le llega en el JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SEG_USUARIO_GRUPO",
            joinColumns = {@JoinColumn(name = "USUARIO")},
            inverseJoinColumns = {@JoinColumn(name = "GRUPO")})
    private Set<Grupo> grupos = new HashSet<>();

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "bloqueado")
    private Boolean bloqueado;

    @Column(name = "root")
    private Boolean root;

    @Column(name = "correo_e")
    @Email
    private String correo;

    @Column(name = "telefono")
    private String telefono;


    @Column(name = "idioma")
    private String idioma;


    @Column(name = "funcion_inicial")
    private Long funcionInicial;

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Grupo grupo : grupos) {
            String role = "ROLE_".concat(grupo.getNombre()).toUpperCase();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
            for (FuncionAsignada funcionAsignada : grupo.getFuncionesAsginadas()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(funcionAsignada.getFuncion().getIdPagina()));
            }
        }
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !bloqueado;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !bloqueado;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !bloqueado;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
