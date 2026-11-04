/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventicket.repository;

import com.eventicket.domain.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author matth
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
 
    // Consulta derivada: categorías para el filtro, en orden alfabético.
    List<Categoria> findAllByOrderByNombreAsc();
 
    Optional<Categoria> findByNombre(String nombre);
}
 
