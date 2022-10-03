package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import com.egg.proyectoFinal.services.impl.OrdenServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import com.egg.proyectoFinal.services.impl.ServcicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/views/ordenes")
public class OrdenController {

    @Autowired
    private OrdenServiceImpl ordenService;

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ServcicioServiceImpl servicioService;

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @GetMapping("/listar")
    public String listar(Model model, @AuthenticationPrincipal UserDetails details){
        String emailC =details.getUsername();
        List<Orden> ordenes = ordenService.findByEmailC(emailC);
        model.addAttribute("ordenes", ordenes);
        return "/views/ordenes/ordenes";
    }
    @GetMapping("/listarp")
    public String listarp(Model model, @AuthenticationPrincipal UserDetails details){
        String emailC =details.getUsername();

        List<Orden> ordenes = ordenService.findByEmailP(emailC);

        model.addAttribute("ordenes", ordenes);
        return "/views/ordenes/ordenes_p";
    }
    @GetMapping("/listar_admin")
    public String listarAdmin(Model model){
        List<Orden> ordenes = ordenService.findAll();
        model.addAttribute("ordenes", ordenes);
        return "/views/ordenes/ordenes_admin";
    }
    @RequestMapping(value = "/form/{id}/orden")
    public String crearOrden(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails details, RedirectAttributes attributes){
        Persona persona = personaService.findById(id);
        String email = persona.getEmail();
        String emailUsuarioLogueado = details.getUsername();

        if(persona.getServicio().getTipo().equals("ninguno")){
            attributes.addFlashAttribute("error", "No puedes contrar a alguien sin oficio!!");
            return "redirect:/views/personas/listar";
        }

        if(!emailUsuarioLogueado.equals(email) && !persona.getServicio().getTipo().equals("ninguno")){
            Orden orden = new Orden();
            model.addAttribute("orden", orden);
            model.addAttribute("persona", persona);
            model.addAttribute("servicios", servicioService.findAll());
            model.addAttribute("titulo", "Formulario de Orden ");
            return "/views/ordenes/form";
        }else{
            attributes.addFlashAttribute("error", "No puedes contrarte a ti mismo!!!");
            return "redirect:/views/personas/listar";
        }
    }
    @PostMapping(value = "/form/{id}/orden")
    public String guardar(@PathVariable Long id, Orden orden, BindingResult result, Model model,
                          RedirectAttributes attributes, @AuthenticationPrincipal UserDetails details){
        if(result.hasErrors()){
            model.addAttribute("orden", "Formulario de Orden");
            return "/views/personas/listar";
        }
        Persona persona = personaService.findById(id);
        orden.setPrestador(persona);
        orden.setEmailp(persona.getEmail());
        orden.setEmailc(details.getUsername());
        ordenService.create(orden);
        model.addAttribute("success", "Orden Agregada con Exito!!");
        return "redirect:/views/personas/listar";
    }
    @GetMapping(value = "/detalle/{id}")
    public String detalle(Model model, @PathVariable("id") Long id){
        if(id>0){
            Orden orden = ordenService.findById(id);
            model.addAttribute("orden", orden);
        }else{
            return "redirect:/views/ordenes/listar";
        }
        return "/views/ordenes/detalle";
    }
    @RequestMapping(value = "/eliminar/{id}")
    @Transactional
    public String eliminar(@PathVariable(value = "id") Long id, Orden orden, Model model){
        if(id>0){
            ordenService.delete(orden, id );
            model.addAttribute("success", "Orden Eliminada con Exito!!");
        }
        return "redirect:/views/ordenes/listar";
    }

}
