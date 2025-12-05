package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.enums.Experiencia;
import com.egg.proyectoFinal.services.impl.ComentarioServiceImpl;
import com.egg.proyectoFinal.services.impl.OrdenServiceImpl;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private ComentarioServiceImpl comentarioService;

    @Autowired
    private OrdenServiceImpl ordenService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Persona> listarUsuariosActivos() {
        return personaService.findPorRol("USER");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Persona> listarTodas() {
        return personaService.findAll();
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public List<Persona> buscarPorNombre(@RequestParam(value = "nombre", required = false) String nombre,
                                         @RequestParam(value = "oficio", required = false) String oficio,
                                         @RequestParam(value = "email", required = false) String email) {
        if (email != null) {
            Persona encontrada = personaService.findByEmail(email);
            return encontrada != null ? List.of(encontrada) : List.of();
        }
        if (oficio != null) {
            return personaService.findByServicioTipo(oficio);
        }
        return personaService.findByNombre(nombre);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Persona> detalle(@PathVariable("id") Long id) {
        Optional<Persona> persona = personaService.porId(id);
        return persona.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona) {
        Persona creada = personaService.create(persona);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Persona> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Persona persona) {
        if (!personaService.porId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        persona.setId(id);
        Persona actualizada = personaService.update(persona, id);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        if (!personaService.porId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personaService.delete(new Persona(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comentarios")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Map<String, Object>> comentarios(@PathVariable Long id) {
        Optional<Persona> persona = personaService.porId(id);
        if (persona.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("persona", persona.get());
        respuesta.put("comentarios", comentarioService.listaComPersona(id));
        respuesta.put("experiencias", Experiencia.values());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/{id}/comentarios")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Comentario> agregarComentario(@PathVariable Long id, @Valid @RequestBody Comentario comentario) {
        Optional<Persona> persona = personaService.porId(id);
        if (persona.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        comentario.setPersona(persona.get());
        Comentario creado = comentarioService.create(comentario);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/ordenes")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Map<String, Object>> ordenes(@PathVariable Long id) {
        Optional<Persona> persona = personaService.porId(id);
        if (persona.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("recibidas", ordenService.findByEmailP(persona.get().getEmail()));
        respuesta.put("realizadas", ordenService.findByEmailC(persona.get().getEmail()));
        return ResponseEntity.ok(respuesta);
    }
}
