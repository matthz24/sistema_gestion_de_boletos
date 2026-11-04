/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventicket.repository;
import com.eventicket.domain.TokenRecuperacion;
import com.eventicket.domain.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author josem
 */
@Repository
public interface TokenRecuperacionRepository extends JpaRepository<TokenRecuperacion, Long> {
    
     // Busca el token por su hash al abrir el enlace del correo.
    Optional<TokenRecuperacion> findByTokenHash(String tokenHash);
    
    // Permite invalidar los tokens anteriores al pedir uno nuevo.
    List<TokenRecuperacion> findByUsuarioAndUsadoFalse(Usuario usuario);
}
