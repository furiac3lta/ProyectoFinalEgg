package com.egg.proyectoFinal.services;

import com.egg.proyectoFinal.entities.Persona;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface PersonaServices {

    Persona create(Persona persona);
    Persona update(Persona persona, Long id);
    List<Persona> findAll();
    Persona findById(Long id);
    void delete(Persona persona, Long id);
    List<Persona> findByNombre(String filtro);

    List<Persona> findByServicioTipo(String filtro);

    Persona findByEmail(String email);

    Optional<Persona> porId(Long id);

    List<Persona> findPorRol(String rol);

    List<String>findCantidadRolLista();

    HashMap<String, Integer> convertirArrayRolAMap(List<String> surveyList);
}
