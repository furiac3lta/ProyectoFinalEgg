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

    // Email del cliente solicitante (se usa para preservar privacidad al exponer datos)
    private String emailc;

    // Email del prestador del servicio
    private String emailp;

    @ManyToOne
    private Persona solicitante;   // <--- CLIENTE real (relación interna)

    @ManyToOne
    private Persona prestador;     // <--- PRESTADOR real (relación interna)

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    private Boolean activo = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedAt;
}
