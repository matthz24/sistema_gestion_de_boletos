/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.config;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
/**
 *
 * @author josem
 */
@ControllerAdvice
public class UsuarioGlobal {
    @ModelAttribute("logueado")
    public boolean logueado(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return auth  !=null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        
    }
}
