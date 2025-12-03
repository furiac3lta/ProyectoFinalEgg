package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.exceptions.MyException;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import com.egg.proyectoFinal.services.impl.UsuarioServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class RegistroController {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private UsuarioServicioImpl usuarioServicio;

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("API operativa");
    }

    @PostMapping("/registro")
    public ResponseEntity<Persona> registrar(@Valid @RequestBody Persona persona) {
        if (persona.getPassword() == null || !persona.getPassword().equals(persona.getConfirmPassword())) {
            return ResponseEntity.badRequest().build();
        }
        usuarioServicio.registrar(persona, persona.getPassword(), persona.getEmail());
        return new ResponseEntity<>(persona, HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Persona> modificar(@PathVariable("id") Long id, @Valid @RequestBody Persona persona) {
        Optional<Persona> existente = personaService.porId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            usuarioServicio.updateRegistrar(existente.get(), persona);
            return ResponseEntity.ok(personaService.findById(id));
        } catch (MyException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
