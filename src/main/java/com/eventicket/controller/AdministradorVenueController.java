/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.controller;

import com.eventicket.datos.VenueForm;
import com.eventicket.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author josem
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/venues")
public class AdministradorVenueController {

    private final VenueService venueService;

    @GetMapping
    public String listar(Model model) {
        prepararLista(model);
        model.addAttribute("venueForm", new VenueForm());
        return "admin/venues";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("venueForm", venueService.obtenerFormulario(id));
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/venues";
        }

        prepararLista(model);
        model.addAttribute("abrirFormulario", true);
        return "admin/venues";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("venueForm") VenueForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararLista(model);
            model.addAttribute("abrirFormulario", true);
            return "admin/venues";
        }

        try {
            venueService.guardar(form);
            redirectAttributes.addFlashAttribute(
                    "aviso", "El recinto se guardó correctamente."
            );
            return "redirect:/admin/venues";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("venue", e.getMessage());
            prepararLista(model);
            model.addAttribute("abrirFormulario", true);
            return "admin/venues";
        }
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Long id,
            RedirectAttributes redirectAttributes) {
        try {
            venueService.eliminar(id);
            redirectAttributes.addFlashAttribute(
                    "aviso", "El recinto se eliminó correctamente."
            );
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/venues";
    }

    private void prepararLista(Model model) {
        model.addAttribute("venues", venueService.listar());
        model.addAttribute("menuActivo", "venues");
        model.addAttribute("abrirFormulario", false);
    }
}
