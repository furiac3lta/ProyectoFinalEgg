package com.egg.proyectoFinal.controllers;

import com.egg.proyectoFinal.dto.AuthRequest;
import com.egg.proyectoFinal.dto.AuthResponse;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.exceptions.MyException;
import com.egg.proyectoFinal.security.JwtUtil;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import com.egg.proyectoFinal.services.impl.UsuarioServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("API operativa");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("");
        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), role));
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
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
