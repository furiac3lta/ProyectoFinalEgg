package com.egg.proyectoFinal.entities;

import com.egg.proyectoFinal.enums.EstadoOrden;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orden extends BaseEntity {

    private String detalle;

    @ManyToOne
    private Persona solicitante;   // <--- CLIENTE (GUEST)

    @ManyToOne
    private Persona prestador;     // <--- PROVEEDOR (USER)

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    private Boolean activo = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedAt;
}
