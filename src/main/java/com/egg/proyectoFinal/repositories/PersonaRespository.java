package com.egg.proyectoFinal.repositories;

import com.egg.proyectoFinal.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PersonaRespository extends JpaRepository<Persona, Long> {
    @Transactional(readOnly = true)
    @Query(value = "SELECT * from persona WHERE persona.nombre LIKE %:filtro%", nativeQuery = true)
    List<Persona> findByNombre(String filtro);

    @Transactional(readOnly = true)
    List<Persona> findByServicioTipo(String filtro);

    @Transactional(readOnly = true)
    Persona findByEmail(String email);

    @Transactional(readOnly = true)
    @Query(value = "SELECT * from persona WHERE persona.rol LIKE %:filtro%", nativeQuery = true)
    List<Persona> findPorRol(String filtro);

    @Transactional(readOnly = true)
    @Query(value = "SELECT rol, COUNT(*) FROM persona GROUP BY  rol", nativeQuery = true)
    List<String> findCantidadRolLista();

}
