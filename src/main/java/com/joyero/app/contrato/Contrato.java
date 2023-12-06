package com.joyero.app.contrato;

import com.joyero.app.cliente.Cliente;
import com.joyero.base.Entidad;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contrato")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public class Contrato implements Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "lugartasacion")
    private String lugartasacion;

    @Column(name = "tipocontrato")
    @Enumerated(EnumType.STRING)
    private TipoContrato tipoContrato;

    @Column(name = "mercado")
    @Enumerated(EnumType.STRING)
    private Mercado mercado;

    @Column(name = "fechaalta")
    private LocalDateTime fechaAlta;

    @Column(name = "fechacierre")
    private LocalDateTime fechaCierre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente", insertable = false, updatable = false)
    @ToString.Exclude
    private Cliente cliente;

    @Column(name = "cliente")
    private Long clienteId;

    @Column(name = "tasador")
    private String tasador;

    @Column(name = "personafirma")
    private String personafirma;


}