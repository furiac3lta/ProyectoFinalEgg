package com.egg.proyectoFinal.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="id",updatable=false,nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
