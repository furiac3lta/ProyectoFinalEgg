package com.egg.proyectoFinal.services.impl;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.repositories.ComentarioRepository;
import com.egg.proyectoFinal.services.ComentarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ComentarioServiceImpl implements ComentarioServices {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    @Transactional
    public Comentario create(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    @Override
    @Transactional
    public Comentario update(Comentario comentario, Long id) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            Comentario comentarioUpdate = comentarioOptional.get();
            comentarioUpdate = comentarioRepository.save(comentario);
            return comentarioUpdate;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(Comentario comentario, Long id) {
        try {
            Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
            Comentario comentarioUpdate = comentarioOptional.get();
            comentarioUpdate.setActivo(false);
            comentarioRepository.save(comentarioUpdate);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Comentario findById(Long id) {
        Optional<Comentario> opt = comentarioRepository.findById(id);
        Comentario comentario = opt.get();
        return comentario;
    }

    @Override
    public List<Comentario> listaComPersona(Long id) {
        return comentarioRepository.listaComPersona(id);
    }

    @Override
    public List<String> listarCantidadComentariosPorExperiencia() {
        List<String> stringList = comentarioRepository.listarCantidadComentariosPorExperiencia();
        System.out.println(stringList.toString());
        List<String> words = new ArrayList<String>();
        stringList.stream().forEach(str -> {
            words.addAll(Arrays.asList(str.split(",")));
        });
        System.out.println(words.toString());
        return words;
    }

    @Override
    public HashMap<String, Integer> convertirArrayComentariosAMap(List<String> surveyList) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < surveyList.size(); i+=2) {
            hashMap.put(surveyList.get(i), Integer.valueOf(surveyList.get(i+1)));
        }
        return hashMap;
    }

}
