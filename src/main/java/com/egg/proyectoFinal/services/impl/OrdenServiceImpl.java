package com.egg.proyectoFinal.services.impl;

import com.egg.proyectoFinal.entities.Orden;
import com.egg.proyectoFinal.entities.Persona;
import com.egg.proyectoFinal.repositories.OrdenRepository;
import com.egg.proyectoFinal.services.OrdenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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
        return ordenRepository.findById(id).map(ordenPersistida -> {
            if (orden.getDetalle() != null) {
                ordenPersistida.setDetalle(orden.getDetalle());
            }
            if (orden.getEmailc() != null) {
                ordenPersistida.setEmailc(orden.getEmailc());
            }
            if (orden.getEmailp() != null) {
                ordenPersistida.setEmailp(orden.getEmailp());
            }
            if (orden.getPrestador() != null) {
                ordenPersistida.setPrestador(orden.getPrestador());
            }
            if (orden.getActivo() != null) {
                ordenPersistida.setActivo(orden.getActivo());
            }
            if (orden.getCreatedAt() != null) {
                ordenPersistida.setCreatedAt(orden.getCreatedAt());
            }
            if (orden.getFinishedAt() != null) {
                ordenPersistida.setFinishedAt(orden.getFinishedAt());
            }

            return ordenRepository.save(ordenPersistida);
        }).orElse(null);
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
        return opt.orElse(null);
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

    @Override
    @Transactional
    public Orden finalizar(Long id) {
        return ordenRepository.findById(id).map(ordenPersistida -> {
            ordenPersistida.setFinishedAt(new Date());
            ordenPersistida.setActivo(false);
            return ordenRepository.save(ordenPersistida);
        }).orElse(null);
    }
}
