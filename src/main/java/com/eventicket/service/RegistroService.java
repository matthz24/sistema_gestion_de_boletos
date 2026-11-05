/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.service;
import com.eventicket.domain.RolUsuario;
import com.eventicket.domain.Usuario;
import com.eventicket.datos.RegistroForm;
import com.eventicket.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author josem
 */
@Service
@RequiredArgsConstructor
public class RegistroService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional
    public void registrar(RegistroForm form){
        String correo= form.getCorreo().trim().toLowerCase(Locale.ROOT);
        
        if(usuarioRepository.existsByCorreoIgnoreCase(correo)){
            throw new IllegalArgumentException("Ya existe una cuenta con ese correo");
            
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(form.getNombre().trim());
        usuario.setApellidos(form.getApellidos().trim());
        usuario.setCorreo(correo);
        usuario.setContrasena(passwordEncoder.encode(form.getContrasena()));
        
        //El rol se asigna acá no lee el formulario 
        usuario.setRol(RolUsuario.COMPRADOR);
        usuario.setActivo(true);
        usuario.setAprobado(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setFechaAceptacionTerminos(LocalDateTime.now());
        
        usuarioRepository.save(usuario);
    }
}
