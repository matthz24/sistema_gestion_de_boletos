/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author josem
 */
@Controller
@RequestMapping("/auth")
public class RecuperarController {

    @GetMapping("/recuperar")
    public String recuperar() {
        return "auth/recuperar";
    }
}
