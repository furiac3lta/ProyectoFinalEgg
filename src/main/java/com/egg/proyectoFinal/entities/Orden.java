package com.egg.proyectoFinal.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orden extends BaseEntity{

    private String detalle;

    private String emailc;

    private String emailp;

    @OneToOne
    private Persona prestador;

    private Boolean activo = true;

    @Temporal(TemporalType.TIMESTAMP)
    //@DateTimeFormat(fallbackPatterns = "yyyy-MM-dd")
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedAt = new Date();

}
