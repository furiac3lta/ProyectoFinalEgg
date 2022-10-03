package com.egg.proyectoFinal.repositories;

import com.egg.proyectoFinal.entities.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT * from orden WHERE orden.emailc LIKE %:filtro%", nativeQuery = true)
    List<Orden> findByEmailC(@Param("filtro") String filtro);

    @Transactional(readOnly = true)
    @Query(value = "SELECT * from orden WHERE orden.emailp LIKE %:filtro%", nativeQuery = true)
    List<Orden> findByEmailP(@Param("filtro") String filtro);
}
