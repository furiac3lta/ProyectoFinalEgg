package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.exceptions.MyException;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import com.egg.proyectoFinal.services.impl.ServcicioServiceImpl;
import com.egg.proyectoFinal.services.impl.UsuarioServicioImpl;
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
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RegistroController {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ServcicioServiceImpl servcicioService;

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @Autowired
    private UsuarioServicioImpl usuarioServicio;

    @GetMapping(value = "/")
    public String index(Model model) {
        return "index";
    }
    @GetMapping("/perfil/{id}")
    public String perfil(@PathVariable Long id, Model model){
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Persona personaLogin = this.personaService.findByEmail(userDetail.getUsername());
        Persona persona = personaService.findById(id);
        if(personaLogin == persona){
            model.addAttribute("persona", persona);
            return "/perfil";
        }else{
            model.addAttribute("error", "Ud no puede ingresar aqui!!!");
            return "/index";
        }

    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro";
    }

    @RequestMapping(value = "/registro")
    public String registrarPersona(Map<String, Object> model) {
        Persona persona = new Persona();
        List<Servicio> servicios = servcicioService.findAll();
        model.put("servicios", servicios);
        model.put("persona", persona);
        model.put("titulo", "Formulario de Persona");
        return "/registro";
    }


    @PostMapping("/registro")
    public String guardar(@Valid Persona persona, BindingResult result, Model model,
                          @RequestParam("file") MultipartFile imagen, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "formulario de persona");
            model.addAttribute("warning", "Datos incorrectos favor de recargar la pagina!!");
            return "/registro";
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
        if (!persona.getPassword().equals(persona.getConfirmPassword()) || persona.getPassword() == null) {
            return "/registro";
        }
        model.addAttribute("servicios", servcicioService.findAll());
        model.addAttribute("persona", persona);
        usuarioServicio.registrar(persona, persona.getPassword(), persona.getEmail());
        model.addAttribute("success", "Usuario registrado con Exito!!");
        return "/index";
    }


    @RequestMapping("/modificar/{id}")
    public String modificarRegistro(@PathVariable("id") Long id, RedirectAttributes attributes, Model model) {
        Persona persona = new Persona();
        if (id > 0) {
            persona = personaService.findById(id);
        } else {
            model.addAttribute("error", "Usuario no Encontrado!!");
            return "redirect:/views/personas/listar";
        }
        List<Servicio> servicios = servcicioService.findAll();
        model.addAttribute("servicios", servicios);
        model.addAttribute("persona", persona);
        return "/modificar";

    }

//    @PostMapping("/modificar")
//    public String editar(@Valid Persona persona, BindingResult result, Model model,
//                         @RequestParam("file") MultipartFile imagen, RedirectAttributes attributes) {
//
//        Persona personaEdit = personaService.findByEmail(persona.getEmail());
//
//        if (result.hasErrors()) {
//            model.addAttribute("titulo", "formulario de persona");
//            model.addAttribute("warning", "Datos incorrectos favor de recargar la pagina!!");
//            return "/modificar";
//        }
//        if (!imagen.isEmpty()) {
//            String rutaAbsoluta = "//home//marce//Documentos//imagen";
//            try {
//                byte[] bytesImg = imagen.getBytes();
//                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
//                Files.write(rutaCompleta, bytesImg);
//                persona.setFoto(imagen.getOriginalFilename());
//            } catch (IOException e) {
//
//                e.printStackTrace();
//            }
//        }
//        if (!persona.getPassword().equals(persona.getConfirmPassword()) || persona.getPassword() == null) {
//            return "/modificar";
//        }
//        model.addAttribute("servicios", servcicioService.findAll());
//        model.addAttribute("persona", persona);
//        try {
//            usuarioServicio.updateRegistrar(personaEdit, persona);
//            model.addAttribute("success", "Usuario Editado con Exito!!" + persona);
//            return "redirect:/modificar";
//        } catch (MyException ex) {
//            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
//            model.addAttribute("error", "Ha ocurrido un error");
//        }
//        return "redirect:/modificar";
//    }
@PostMapping("/modificar")
public String editar(@Valid Persona persona, BindingResult result, Model model,
                     @RequestParam("file") MultipartFile imagen, RedirectAttributes attributes) {

    Persona personaEdit = personaService.findByEmail(persona.getEmail());


    if (result.hasErrors()) {
        model.addAttribute("titulo", "formulario de persona");
        model.addAttribute("warning", "Datos incorrectos favor de recargar la pagina!!");
        return "/modificar";
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
    try {
        usuarioServicio.updateRegistrar(personaEdit, persona);
        model.addAttribute("success", "Usuario Editado con Exito!!"+persona);
    } catch (MyException ex) {
        Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        model.addAttribute("error", "Ha ocurrido un error");
    }
    return "redirect:/views/personas/listar";
}
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model, RedirectAttributes attributes,
                        Principal principal, @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrecta!!");
        }
        if (principal != null) {
            model.addAttribute("warning", "Sesion ya iniciada!!");
        }
        if (logout != null) {
            model.addAttribute("success", "Ha finalizado Sesion!!");
        }
        return "/login";
    }

    @GetMapping("/modificar_rol/{id}")
    public String cambiarRol(@PathVariable Long id, RedirectAttributes attributes, @AuthenticationPrincipal UserDetails details) {
        Persona persona = personaService.findById(id);
        String email = persona.getEmail();
        String emailUsuarioLogueado = details.getUsername();
        if (emailUsuarioLogueado.equals(email)) {
            usuarioServicio.cambiarRol(id);
            attributes.addFlashAttribute("success", "cambio de rol exitoso!!");
            return "redirect:/views/personas/listar";
        }
        attributes.addFlashAttribute("error", "Operacion no permitida!!");
        System.out.println("ud no puede cambiar de rol a este usuario");
        return "redirect:/views/personas/listar";
    }
}
