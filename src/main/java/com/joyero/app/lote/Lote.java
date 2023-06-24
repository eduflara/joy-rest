package com.joyero.app.lote;

import com.joyero.app.Cliente;
import com.joyero.app.contrato.Contrato;
import com.joyero.base.Entidad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Lote implements Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "objeto")
    @Enumerated(EnumType.STRING)
    private Objeto objeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contrato", insertable = false, updatable = false)
    @ToString.Exclude
    private Contrato contrato;

    @Column(name = "contrato")
    private Long contratoId;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "metal")
    @Enumerated(EnumType.STRING)
    private Metal metal;

    @Column(name = "gema")
    @Enumerated(EnumType.STRING)
    private Gema gema;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "cantidad")
    private Double cantidad;





}