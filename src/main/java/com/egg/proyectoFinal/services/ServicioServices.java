package com.egg.proyectoFinal.services;


import com.egg.proyectoFinal.entities.Servicio;

import java.util.List;

public interface ServicioServices {

    public Servicio create(Servicio servicio);
    public Servicio update(Servicio servicio, Long id);
    public List<Servicio> findAll();
    public Servicio findById(Long id);
    public void delete(Servicio servicio, Long id);
}
