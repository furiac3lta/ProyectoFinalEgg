package com.egg.proyectoFinal.services.impl;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.repositories.OrdenRepository;
import com.egg.proyectoFinal.services.OrdenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenServiceImpl implements OrdenServices {

    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    @Transactional
    public Orden create(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    @Transactional
    public List<Orden> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    @Transactional
    public Orden update(Orden orden, Long id) {
        try {
            Orden ordenOptional = ordenRepository.findById(id).get();
            ordenOptional = ordenRepository.save(orden);
            return ordenOptional;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Orden orden, Long id) {
        try {
            Optional<Orden> ordenOptional = ordenRepository.findById(id);
            Orden ordenUpdate = ordenOptional.get();
            ordenUpdate.setActivo(false);
            ordenRepository.save(ordenUpdate);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public Orden findById(Long id) {
        Optional<Orden> opt = ordenRepository.findById(id);
        return opt.get();
    }

    @Override
    @Transactional
    public List<Orden> findByEmailC(String email) {
        return ordenRepository.findByEmailC(email);
    }

    @Override
    public List<Orden> findByEmailP(String email) {
        return ordenRepository.findByEmailP(email);
    }
}
