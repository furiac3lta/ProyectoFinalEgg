package com.egg.proyectoFinal.services.impl;

import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.entities.Servicio;
import com.egg.proyectoFinal.repositories.ServicioRepository;
import com.egg.proyectoFinal.services.ServicioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServcicioServiceImpl implements ServicioServices {
    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    @Transactional
    public Servicio create(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    @Transactional
    public Servicio update(Servicio servicio, Long id) {
        try {
            Optional<Servicio> servicioOptional = servicioRepository.findById(id);
            Servicio servicioUpdate = servicioOptional.get();
            servicioUpdate = servicioRepository.save(servicio);
            return servicioUpdate;
        }catch(Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Servicio servicio, Long id) {
        try {
            Optional<Servicio> servicioOptional = servicioRepository.findById(id);
            Servicio servicioUpdate = servicioOptional.get();
            servicioUpdate.setActivo(false);
            servicioRepository.save(servicioUpdate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Servicio findById(Long id) {
        try {
            Optional<Servicio> opt = servicioRepository.findById(id);
            return opt.get();
        }catch(Exception e){
            return null;
        }

    }
}
