package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.services.impl.OrdenServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenServiceImpl ordenService;

    @Autowired
    private PersonaServiceImpl personaService;

    @GetMapping
    public List<Orden> listarTodas() {
        return ordenService.findAll();
    }

    @GetMapping("/cliente/{email}")
    public List<Orden> listarPorCliente(@PathVariable String email) {
        return ordenService.findByEmailC(email);
    }

    @GetMapping("/prestador/{email}")
    public List<Orden> listarPorPrestador(@PathVariable String email) {
        return ordenService.findByEmailP(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> detalle(@PathVariable("id") Long id) {
        Optional<Orden> orden = Optional.ofNullable(ordenService.findById(id));
        return orden.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/persona/{id}")
    public ResponseEntity<Orden> crear(@PathVariable Long id, @Valid @RequestBody Orden orden) {
        Persona persona = personaService.findById(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }
        orden.setPrestador(persona);
        orden.setEmailp(persona.getEmail());
        Orden creada = ordenService.create(orden);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orden> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Orden orden) {
        if (ordenService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        orden.setId(id);
        Orden actualizada = ordenService.update(orden, id);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (ordenService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        ordenService.delete(new Orden(), id);
        return ResponseEntity.noContent().build();
    }
}
