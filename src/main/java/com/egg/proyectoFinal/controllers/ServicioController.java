package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.services.impl.ServcicioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {
    @Autowired
    private ServcicioServiceImpl servcicioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Servicio> listar() {
        return servcicioService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Servicio> detalle(@PathVariable("id") Long id) {
        Optional<Servicio> servicio = Optional.ofNullable(servcicioService.findById(id));
        return servicio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Servicio> crear(@Valid @RequestBody Servicio servicio) {
        Servicio creado = servcicioService.create(servicio);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Servicio> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Servicio servicio) {
        if (servcicioService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servicio.setId(id);
        Servicio actualizado = servcicioService.update(servicio, id);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (servcicioService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        servcicioService.delete(new Servicio(), id);
        return ResponseEntity.noContent().build();
    }
}
