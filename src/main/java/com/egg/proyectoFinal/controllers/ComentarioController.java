package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/views/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @Autowired
    private PersonaServiceImpl personaService;

    @GetMapping("/listar")
    public String listar(Model model){
        List<Comentario> comentarios = comentarioService.findAll();
        model.addAttribute("comentarios", comentarios);
        return "/views/comentarios/comentarios";
    }

    @GetMapping(value="/detalle/{id}")
    public String detalle(Model model, @PathVariable("id") Long id){
        if(id>0){
            Comentario comentario = comentarioService.findById(id);
            model.addAttribute("comentario", comentario);
        }else{
            return "redirect:/views/comentarios/listar";
        }
        return "/views/comentarios/detalle";
    }

    @RequestMapping(value="/eliminar/{id}")
    @Transactional
    public String eliminar(@PathVariable(value="id") Long id, Comentario comentario, Model model){
        if(id>0){
            comentarioService.delete(comentario, id);
            model.addAttribute("error", "Comentario eliminado con exito!!");
        }
        return "redirect:/views/comentarios/listar";
    }


}
