package com.egg.proyectoFinal.entities;

import com.egg.proyectoFinal.enums.Experiencia;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comentario extends BaseEntity {

    @NotNull
    private String opinion;

    @Enumerated(EnumType.STRING)
    private Experiencia experiencia;

    private String imagen;

    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    private String autor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date autorCreateAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date autorUpdateAt = new Date();

}