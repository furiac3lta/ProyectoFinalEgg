package com.egg.proyectoFinal.services.impl;

import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.enums.Rol;
import com.egg.proyectoFinal.exceptions.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UserDetailsService {

    @Autowired
    private PersonaServiceImpl personaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Persona persona = personaService.findByEmail(email);

        if (persona == null) {
            throw new UsernameNotFoundException("No se encontró el usuario " + email);
        }

        String rol = "ROLE_" + persona.getRol().name();
        GrantedAuthority authority = new SimpleGrantedAuthority(rol);

        return new User(
                persona.getEmail(),
                persona.getPassword(),
                Collections.singletonList(authority)
        );
    }

    @Transactional
    public Persona registrar(Persona persona, String password, String email) {
        persona.setEmail(email);
        persona.setPassword(passwordEncoder.encode(password));
        persona.setRol(Rol.GUEST);

        return personaService.create(persona);
    }
    @Transactional
    public void updateRegistrar(Persona personaEdit, Persona persona) throws MyException {
        personaEdit.setNombre(persona.getNombre());
        personaEdit.setApellido(persona.getApellido());
        personaEdit.setEmail(persona.getEmail());
        personaEdit.setProvincia(persona.getProvincia());
        personaEdit.setLocalidad(persona.getLocalidad());
        personaEdit.setServicio(persona.getServicio());
        personaEdit.setFoto(persona.getFoto());

        // Solo actualiza contraseña si vino una nueva
        if (persona.getPassword() != null && !persona.getPassword().isBlank()) {
            personaEdit.setPassword(passwordEncoder.encode(persona.getPassword()));
        }

        personaEdit.setUpdatedAt();
        personaService.update(personaEdit, personaEdit.getId());
    }

}
