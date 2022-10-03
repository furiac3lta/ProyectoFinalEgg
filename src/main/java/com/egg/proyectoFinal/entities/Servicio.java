package com.egg.proyectoFinal.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Servicio extends BaseEntity {

    private String tipo;
    private String detalle;

    private Boolean activo = true;

    @Override
    public String toString() {
        return tipo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @PreUpdate
    public void setUpdateAt() {
        this.updateAt = new Date();
    }
}
