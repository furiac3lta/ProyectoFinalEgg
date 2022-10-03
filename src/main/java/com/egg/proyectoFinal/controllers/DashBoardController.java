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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
@Controller
public class DashBoardController {
    @Autowired
    private OrdenServiceImpl ordenService;

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ServcicioServiceImpl servicioService;

    @Autowired
    private ComentarioServiceImpl comentarioService;
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        List<Orden> ordenes = ordenService.findAll();
        List<Comentario> comentarios = comentarioService.findAll();
        List<Persona> personas = personaService.findAll();
        List<Servicio> servicios = servicioService.findAll();
        model.addAttribute("ordenes", ordenes);
        model.addAttribute("comentarios", comentarios);
        model.addAttribute("personas", personas);
        model.addAttribute("servicios", servicios);
        return "dashboard";
    }
    @GetMapping("/dashboard_reportes")
    public @ResponseBody List<Persona> listar(){
        return personaService.findAll();
    }

    @GetMapping("/displayBarGraph")
    public String graficoRol(Model model) {
        List<String>surveyList = personaService.findCantidadRolLista();
        HashMap<String, Integer> surveyMap = personaService.convertirArrayRolAMap(surveyList);
        model.addAttribute("surveyMap", surveyMap);
        return "barGraph";
    }
    @GetMapping("/displayBarGraph_1")
    public String graficaComentarios(Model model) {
        List<String>surveyList = comentarioService.listarCantidadComentariosPorExperiencia();
        HashMap<String, Integer> surveyMap = comentarioService.convertirArrayComentariosAMap(surveyList);
        model.addAttribute("surveyMap", surveyMap);
        return "barGraph_1";
    }

}

