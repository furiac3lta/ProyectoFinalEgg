package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Comentario> listar() {
        return comentarioService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Comentario> detalle(@PathVariable("id") Long id) {
        Optional<Comentario> comentario = Optional.ofNullable(comentarioService.findById(id));
        return comentario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Comentario> crear(@RequestBody Comentario comentario) {
        Comentario creado = comentarioService.create(comentario);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable(value = "id") Long id) {
        if (comentarioService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        comentarioService.delete(new Comentario(), id);
        return ResponseEntity.noContent().build();
    }
}
