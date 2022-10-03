package com.egg.proyectoFinal.repositories;

import com.egg.proyectoFinal.entities.Comentario;
import com.egg.proyectoFinal.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    @Transactional(readOnly = true)
    @Query(value = "SELECT * from comentario WHERE comentario.id_persona LIKE %:filtro%", nativeQuery = true)
    List<Comentario> listaComPersona(@Param("filtro") Long id);

    @Transactional(readOnly = true)
    @Query(value = "SELECT experiencia, COUNT(*) FROM comentario GROUP BY  experiencia", nativeQuery = true)
    List<String> listarCantidadComentariosPorExperiencia();

}
