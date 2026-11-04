/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.service;
import com.eventicket.domain.Usuario;
import com.eventicket.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 *
 * @author josem
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService{
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String correo)throws UsernameNotFoundException{
        Usuario usuario= usuarioRepository.findByCorreoIgnoreCase(correo).orElseThrow(() -> new UsernameNotFoundException("Credenciales no válidas"));
        
        return User.withUsername(usuario.getCorreo())
                .password(usuario.getContrasena())
                .roles(usuario.getRol().name())
                .disabled(!usuario.isActivo() || !usuario.isAprobado())
                .build();
    }
}
