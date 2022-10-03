package com.egg.proyectoFinal.services.impl;


import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.repositories.PersonaRespository;
import com.egg.proyectoFinal.repositories.ServicioRepository;
import com.egg.proyectoFinal.services.PersonaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PersonaServiceImpl implements PersonaServices {

    @Autowired
    private PersonaRespository personaRespository;

    @Autowired
    private ServicioRepository servicioRepository;


    @Override
    @Transactional
    public Persona create(Persona persona) {
        try {
            return personaRespository.save(persona);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public List<Persona> findAll() {
        return personaRespository.findAll();
    }

    @Override
    @Transactional
    public Persona update(Persona persona, Long id) {
             Persona personaOptional = personaRespository.findById(id).get();
             personaOptional = personaRespository.save(persona);
             return personaOptional;
    }

    //softDelete
    @Override
    @Transactional
    public void delete(Persona persona, Long id) {
        try {
            Optional<Persona> personaOptional = personaRespository.findById(id);
            Persona personaUpdate = personaOptional.get();
            personaUpdate.setEmail("");
            personaUpdate.setActivo(false);
            personaRespository.save(personaUpdate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public Persona findById(Long id) {
        Optional<Persona> opt = personaRespository.findById(id);
        return opt.get();
    }

    @Override
    @Transactional
    public Optional<Persona> porId(Long id) {
        Optional<Persona> opt = personaRespository.findById(id);
        return opt;
    }

    @Override
    @Transactional
    public List<Persona> findPorRol(String rol) {
        return personaRespository.findPorRol(rol);
    }


    @Override
    public List<String> findCantidadRolLista() {
        List<String> stringList = personaRespository.findCantidadRolLista();
        System.out.println(stringList.toString());
        List<String> words = new ArrayList<String>();
        stringList.stream().forEach(str -> {
            words.addAll(Arrays.asList(str.split(",")));
        });
        System.out.println(words.toString());

        return words;
    }


    @Override
    public List<Persona> findByNombre(String filtro) {
        return personaRespository.findByNombre(filtro);
    }

    @Override
    public List<Persona> findByServicioTipo(String filtro) {
        return personaRespository.findByServicioTipo(filtro);
    }

    @Override
    public Persona findByEmail(String email){
        return personaRespository.findByEmail(email);
    };

    @Override
    public HashMap<String, Integer> convertirArrayRolAMap(List<String> surveyList) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < surveyList.size(); i+=2) {
            hashMap.put(surveyList.get(i), Integer.valueOf(surveyList.get(i+1)));
        }
        return hashMap;
    }


}
