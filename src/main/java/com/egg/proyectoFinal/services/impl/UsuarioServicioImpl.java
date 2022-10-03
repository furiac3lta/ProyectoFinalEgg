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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements UserDetailsService {

    @Autowired
    private PersonaServiceImpl personaService;

    @Transactional
    public void registrar(Persona persona, String password, String email) {
        try {
            persona.setEmail(email);
            persona.setPassword(new BCryptPasswordEncoder().encode(password));
            persona.setRol(Rol.GUEST);

            personaService.create(persona);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void cambiarRol(Long id) {
        try {
            Persona respuesta = personaService.findById(id);
            if (respuesta != null) {
                Persona persona = respuesta;
                if (persona.getRol().equals(Rol.GUEST)) {
                    persona.setRol(Rol.USER);
                    personaService.update(persona, id);

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Persona persona = personaService.findByEmail(email);

            if (persona != null) {
                List<GrantedAuthority> permisos = new ArrayList<>();
                GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + persona.getRol().toString());
                permisos.add(p);
                return new User(persona.getEmail(), persona.getPassword(), permisos);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void updateRegistrar(Persona personaEdit, Persona persona) throws MyException {
        try {
            personaEdit.setNombre(persona.getNombre());
            personaEdit.setApellido(persona.getApellido());
            personaEdit.setEmail(persona.getEmail());
            personaEdit.setServicio(persona.getServicio());
            personaEdit.setFoto(persona.getFoto());
            personaEdit.setPassword(new BCryptPasswordEncoder().encode(persona.getPassword()));
            persona.setUpdatedAt();
            personaService.update(personaEdit, personaEdit.getId());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}


