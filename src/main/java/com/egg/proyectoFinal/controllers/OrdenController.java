package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.services.impl.OrdenServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Orden> listarTodas() {
        return ordenService.findAll();
    }

    @GetMapping("/cliente/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Orden> listarPorCliente(@PathVariable String email) {
        return ordenService.findByEmailC(email);
    }

    @GetMapping("/prestador/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Orden> listarPorPrestador(@PathVariable String email) {
        return ordenService.findByEmailP(email);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Orden> detalle(@PathVariable("id") Long id) {
        Optional<Orden> orden = Optional.ofNullable(ordenService.findById(id));
        return orden.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/persona/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER', 'GUEST')")
    public ResponseEntity<Orden> crear(@PathVariable Long id, @Valid @RequestBody Orden orden) {
        Persona persona = personaService.findById(id);
        if (persona == null) {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Persona solicitante = personaService.findByEmail(authentication.getName());
            if (solicitante != null) {
                orden.setEmailc(solicitante.getEmail());
            }
        }

        orden.setPrestador(persona);
        orden.setEmailp(persona.getEmail());
        Orden creada = ordenService.create(orden);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Orden> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Orden orden) {
        if (ordenService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        orden.setId(id);
        Orden actualizada = ordenService.update(orden, id);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (ordenService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        ordenService.delete(new Orden(), id);
        return ResponseEntity.noContent().build();
    }
}
