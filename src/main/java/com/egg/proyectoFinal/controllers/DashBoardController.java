package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import com.egg.proyectoFinal.services.impl.OrdenServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import com.egg.proyectoFinal.services.impl.ServcicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
    @Autowired
    private OrdenServiceImpl ordenService;

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ServcicioServiceImpl servicioService;

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @GetMapping
    public Map<String, Object> resumen() {
        Map<String, Object> resumen = new HashMap<>();
        resumen.put("ordenes", ordenService.findAll());
        resumen.put("comentarios", comentarioService.findAll());
        resumen.put("personas", personaService.findAll());
        resumen.put("servicios", servicioService.findAll());
        return resumen;
    }

    @GetMapping("/personas")
    public List<Persona> personas() {
        return personaService.findAll();
    }

    @GetMapping("/roles")
    public Map<String, Integer> graficoRol() {
        List<String> surveyList = personaService.findCantidadRolLista();
        return personaService.convertirArrayRolAMap(surveyList);
    }

    @GetMapping("/comentarios")
    public Map<String, Integer> graficoComentarios() {
        List<String> surveyList = comentarioService.listarCantidadComentariosPorExperiencia();
        return comentarioService.convertirArrayComentariosAMap(surveyList);
    }
}
