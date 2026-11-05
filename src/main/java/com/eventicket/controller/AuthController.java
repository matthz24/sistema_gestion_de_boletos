/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.controller;

import com.eventicket.datos.RegistroForm;
import com.eventicket.service.RegistroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.nio.charset.StandardCharsets;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author josem
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RegistroService registroService;

    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("registroForm", new RegistroForm());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String guardar(@Valid @ModelAttribute("registroForm") RegistroForm form,
            BindingResult bindingResult) {

        if (!bindingResult.hasFieldErrors("contrasena")
                && form.getContrasena() != null
                && form.getContrasena().getBytes(StandardCharsets.UTF_8).length > 72) {
            bindingResult.rejectValue("contrasena", "contrasena.larga",
                    "La contraseña es demasiado larga.");
        }
        if (!bindingResult.hasFieldErrors("contrasena")
                && !bindingResult.hasFieldErrors("confirmarContrasena")
                && !form.getContrasena().equals(form.getConfirmarContrasena())) {
            bindingResult.rejectValue("confirmarContrasena", "contrasena.noCoincide",
                    "Las contraseñas no coinciden.");
        }
        if (bindingResult.hasErrors()) {
            return "auth/registro";
        }
        try {
            registroService.registrar(form);
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("correo", "correo.duplicado", e.getMessage());
            return "auth/registro";
        } catch (DataIntegrityViolationException e) {
            bindingResult.rejectValue("correo", "correo.duplicado",
                    "Ya existe una cuenta con ese correo.");
            return "auth/registro";
        }

        return "redirect:/auth/registro?exito";
    }
}
