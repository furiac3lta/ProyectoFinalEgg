package com.egg.proyectoFinal.entities;

import com.egg.proyectoFinal.enums.Rol;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Persona extends BaseEntity{

    @NotEmpty
    @Size(min=3,max=12)
    private String nombre;
    @NotEmpty
    @Size(min=3,max=12)
    private String apellido;

    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    private Long telefono;

    private String foto;

    private Boolean activo = true;
    @OneToOne
    private Servicio servicio;


    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date();
    }
}
