/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventicket.repository;

import com.eventicket.domain.Evento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matth
 */
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Consulta derivada: se usa para armar la lista de meses del filtro de fecha.
    List<Evento> findByActivoTrueOrderByFechaHoraAsc();

    @Query("""
            SELECT e FROM Evento e
            JOIN FETCH e.categoria c
            JOIN FETCH e.venue v
            WHERE e.activo = true
              AND e.fechaHora >= :desde
              AND e.fechaHora < :hasta
              AND (:categoriaId = 0L OR c.id = :categoriaId)
              AND (:venueId = 0L OR v.id = :venueId)
              AND (LOWER(e.titulo) LIKE :texto
                   OR LOWER(c.nombre) LIKE :texto
                   OR LOWER(v.nombre) LIKE :texto
                   OR LOWER(COALESCE(v.ciudad, '')) LIKE :texto)
            ORDER BY e.fechaHora ASC
            """)
    List<Evento> buscar(@Param("texto") String texto,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            @Param("categoriaId") Long categoriaId,
            @Param("venueId") Long venueId);

    @Query("""
            SELECT DISTINCT e FROM Evento e
            JOIN FETCH e.categoria
            JOIN FETCH e.venue
            LEFT JOIN FETCH e.tiposEntrada
            WHERE e.id = :id AND e.activo = true
            """)
    Optional<Evento> findActivoConEntradas(@Param("id") Long id);
}
