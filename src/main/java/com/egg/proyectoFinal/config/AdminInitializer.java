package com.egg.proyectoFinal.config;

import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.enums.Rol;
import com.egg.proyectoFinal.services.impl.PersonaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Persona admin = personaService.findByEmail("admin");
        if (admin == null) {
            Persona persona = new Persona();
            persona.setNombre("Admin");
            persona.setApellido("General");
            persona.setEmail("admin");
            persona.setTelefono(1111111111L);
            persona.setActivo(true);
            persona.setRol(Rol.ADMIN);
            persona.setPassword(passwordEncoder.encode("admin123"));
            personaService.create(persona);
        }
    }
}
