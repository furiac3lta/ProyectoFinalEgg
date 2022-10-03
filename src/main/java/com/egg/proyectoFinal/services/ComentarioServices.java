package com.egg.proyectoFinal.services;

import com.egg.proyectoFinal.entities.Comentario;

import java.util.HashMap;
import java.util.List;

public interface ComentarioServices {

    public Comentario create(Comentario comentario);
    public Comentario update(Comentario comentario, Long id);
    public List<Comentario> findAll();
    public Comentario findById(Long id);
    public void delete(Comentario comentario, Long id);
    List<String> listarCantidadComentariosPorExperiencia();

    public List<Comentario> listaComPersona(Long id);

    HashMap<String, Integer> convertirArrayComentariosAMap(List<String> surveyList);

}
