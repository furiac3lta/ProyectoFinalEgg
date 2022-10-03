package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.enums.Experiencia;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.validation.Valid;


@Controller
@RequestMapping("/views/personas")
public class PersonaController {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ServcicioServiceImpl servcicioService;

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @Autowired
    private UsuarioServicioImpl usuarioServicio;

    @Autowired
    private OrdenServiceImpl ordenService;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Persona> personas = personaService.findPorRol("USER");
        List<Servicio> servicios = servcicioService.findAll();
        model.addAttribute("servicios", servicios);
        // model.addAttribute("titulo", "listado de personas");
        model.addAttribute("personas", personas);
        return "/views/personas/personas";
    }

    @GetMapping(value = "/puente")
    public String puente(Model model) {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Persona persona = this.personaService.findByEmail(userDetail.getUsername());
        System.out.println(persona);
        System.out.println(persona.getNombre());
//        System.out.println(persona.getId());
        model.addAttribute("persona", persona);

        return "/views/personas/puente";
    }



    @GetMapping("/listar_todo")
    public String listarAdmin(Model model) {

        List<Persona> personas = personaService.findAll();

        List<Servicio> servicios = servcicioService.findAll();
        model.addAttribute("servicios", servicios);
        // model.addAttribute("titulo", "listado de personas");
        model.addAttribute("personas", personas);
        return "/views/personas/personas_listar";
    }

    @GetMapping("/listar_nombres")
    public String listarNombres(Model model, @RequestParam(value = "query", required = false) String q) {
        List<Persona> personas = personaService.findByNombre(q);
        model.addAttribute("personas", personas);
        model.addAttribute("resultado", q);
        return "/views/personas/nombres_personas";
    }

    @GetMapping("/listar_oficios")
    public String listarOficios(Model model, @RequestParam(value = "query", required = false) String q) {
        List<Persona> personasOficio = personaService.findByServicioTipo(q);
        model.addAttribute("personasOficio", personasOficio);
        model.addAttribute("resultado", q);
        personasOficio.forEach(System.out::println);
        return "/views/personas/oficios_personas";
    }

    @GetMapping("/listar_email")
    public String listarPorEmail(Model model, @RequestParam(value = "query", required = false) String q) {
        Persona personasEmail = personaService.findByEmail(q);
        model.addAttribute("personasEmail", personasEmail);
        model.addAttribute("resultado", q);
        return "/views/personas/email_personas";
    }

    @GetMapping(value = "/detalle/{id}")
    public String detalle(Model model, @PathVariable("id") Long id) {

        if (id > 0) {
            Persona persona = personaService.findById(id);
            List<Servicio> servicios = servcicioService.findAll();
            model.addAttribute("persona", persona);
            model.addAttribute("servicios", servicios);
            model.addAttribute("comentarios", comentarioService.listaComPersona(id));
        } else {
            return "redirect:/views/personas/listar";
        }
        return "/views/personas/detalle";
    }

    @RequestMapping(value = "/eliminar/{id}")
    @Transactional
    public String eliminar(@PathVariable(value = "id") Long id, Persona persona, RedirectAttributes attributes) {
        if (id > 0) {

            personaService.delete(persona, id);

            attributes.addFlashAttribute("success", "Usuario Eliminado con Exito!!");
        }
        return "redirect:/views/personas/listar";
    }

    @RequestMapping("/comentarios/{id}")
    public String verComentarios(@PathVariable Long id, Model model) {
        model.addAttribute("persona", personaService.findById(id));
        //buscar comentario persona con id determinado
        model.addAttribute("comentarios", comentarioService.findAll());
        model.addAttribute("comentario", new Comentario());
        model.addAttribute("experiencias", Experiencia.values());
        return "views/personas/comentarios";
    }

    @PostMapping(value = "/persona/{id}/comentarios")
    public String agregarComentarios(@Valid Comentario comentario, BindingResult result, @PathVariable Long id, Model model,
                                     RedirectAttributes attributes, @RequestParam("file") MultipartFile imagen
            , @AuthenticationPrincipal UserDetails details) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "formulario de persona");
            return "/views/personas/detalle";
        }
        Persona persona = personaService.findById(id);
        comentario.setPersona(persona);
        List<Orden> ordenes = ordenService.findByEmailC(details.getUsername());
        model.addAttribute("ordenes", ordenes);
        if (!imagen.isEmpty()) {
            String rutaAbsoluta = "//home//marce//Documentos//imagen";
            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                comentario.setImagen(imagen.getOriginalFilename());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        comentario.setAutor(details.getUsername());
        comentarioService.create(comentario);
        model.addAttribute("success", "Comentario Agregado con Exito!!");
        model.addAttribute("persona", persona);
        model.addAttribute("comentarios", comentarioService.listaComPersona(id));

        return "views/personas/detalle";

    }

    @RequestMapping(value = "/form")
    public String crearPersona(Model model) {
        Persona persona = new Persona();
        List<Servicio> servicios = servcicioService.findAll();
        model.addAttribute("servicios", servicios);
        model.addAttribute("persona", persona);
        model.addAttribute("titulo", "Formulario de Persona");
        return "/views/personas/form";
    }


    @PostMapping("/form")
    public String guardar(@Valid Persona persona, BindingResult result, Model model,
                          @RequestParam("file") MultipartFile imagen, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "formulario de persona");
            return "/views/personas/form";
        }
        if (!imagen.isEmpty()) {
            String rutaAbsoluta = "//home//marce//Documentos//imagen";
            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                persona.setFoto(imagen.getOriginalFilename());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        model.addAttribute("servicios", servcicioService.findAll());
        model.addAttribute("persona", persona);
        personaService.create(persona);
        model.addAttribute("success", "Usuario Guardado con Exito!!");
        return "redirect:/views/personas/listar";
    }


}