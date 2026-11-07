package com.eventicket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
    
    @GetMapping("/acceso_denegado")
    public String accesoDenegado() {
        return "acceso_denegado";   // resuelve templates/acceso_denegado.html
    }
}
