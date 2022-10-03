package com.egg.proyectoFinal.services;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;

import java.util.List;

public interface OrdenServices {
    Orden create(Orden orden);
    Orden update(Orden orden, Long id);
    List<Orden> findAll();
    Orden findById(Long id);
    void delete(Orden orden, Long id);

    List<Orden> findByEmailC(String email);

    List<Orden> findByEmailP(String email);
}
