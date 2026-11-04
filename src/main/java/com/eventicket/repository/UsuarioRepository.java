/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eventicket.repository;
import com.eventicket.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author josem
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //esto se usa en el login y en la recuperacion, ign mayusculas
    Optional<Usuario>findByCorreoIgnoreCase(String correo);
    
    //esto evita registrar dos cuentas con el mismo correo
    boolean existsByCorreoIgnoreCase(String correo);
}
