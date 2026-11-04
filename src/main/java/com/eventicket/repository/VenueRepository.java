/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventicket.repository;

import com.eventicket.domain.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matth
 */
@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
 
    // Consulta derivada: recintos para el filtro, en orden alfabético.
    List<Venue> findAllByOrderByNombreAsc();
 
    Optional<Venue> findFirstByNombre(String nombre);
}