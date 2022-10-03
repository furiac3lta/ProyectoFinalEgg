package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.services.impl.ServcicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.transaction.Transactional;
import java.util.List;


@Controller
@RequestMapping("/views/servicios")
public class ServicioController {
    @Autowired
    private ServcicioServiceImpl servcicioService;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Servicio> servicios = servcicioService.findAll();
        model.addAttribute("servicios", servicios);
        return "/views/servicios/servicios";
    }

    @RequestMapping(value = "/form")
    public String crearServicio(Model model) {
        Servicio servicio = new Servicio();
        model.addAttribute("servicio", servicio);
        model.addAttribute("titulo", "Formulario de Servicio ");
        return "/views/servicios/form";
    }

    @PostMapping("/form")
    public String guardar(Servicio servicio, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("servicio", "formulario de servicio");
            return "/views/servicios/form";
        }
        servcicioService.create(servicio);
        model.addAttribute("success", "Servicio Agregado con Exito!!");
        return "redirect:/views/servicios/listar";
    }

    @GetMapping(value = "/form/{id}")
    public String editar(Model model, @PathVariable("id") Long id) {
        Servicio servicio = null;
        if (id > 0) {
            servicio = servcicioService.findById(id);
        } else {
            return "redirect:/views/servicios/listar";
        }
        model.addAttribute("servicio", servicio);
        return "/views/servicios/form";
    }

    @GetMapping(value = "/detalle/{id}")
    public String detalle(Model model, @PathVariable("id") Long id) {

        if (id > 0) {
            Servicio servicio = servcicioService.findById(id);
            model.addAttribute("servicio", servicio);

        } else {
            return "redirect:/views/servicios/listar";
        }
        return "/views/servicios/detalle";
    }

    @RequestMapping(value = "/eliminar/{id}")
    @Transactional
    public String eliminar(@PathVariable(value = "id") Long id, Servicio servicio, Model model) {
        if (id > 0) {
            servcicioService.delete(servicio, id);
            model.addAttribute("success", "Servicio Eliminado con Exito!!");
        }
        return "redirect:/views/servicios/listar";
    }


}
