package com.egg.proyectoFinal.entities;

import com.egg.proyectoFinal.enums.EstadoOrden;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orden extends BaseEntity {

    private String detalle;

    // Email del cliente solicitante (para exponer datos sin revelar Persona completa)
    private String emailc;

    // Email del prestador
    private String emailp;

    @ManyToOne
    private Persona solicitante;   // CLIENTE (usuario que pide la orden)

    @ManyToOne
    private Persona prestador;     // PRESTADOR (usuario que da el servicio)

    @Enumerated(EnumType.STRING)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    private Boolean activo = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedAt;

    // 🆕 AGREGADO DEL PR #7 — flujo de aceptación y rechazo
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rejectedAt;
}
