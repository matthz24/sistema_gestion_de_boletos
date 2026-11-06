/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.controller;
import com.eventicket.datos.EventoForm;
import com.eventicket.repository.CategoriaRepository;
import com.eventicket.repository.VenueRepository;
import com.eventicket.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author josem
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/organizador/eventos")
public class OrganizadorEventoController {
    
    private final EventoService eventoService;
    private final VenueRepository venueRepository;
    private final CategoriaRepository categoriaRepository;

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        prepararFormulario(model);
        model.addAttribute("eventoForm", new EventoForm());
        return "evento/crear";
    }

    @PostMapping("/nuevo")
    public String guardar(@Valid @ModelAttribute("eventoForm") EventoForm form,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararFormulario(model);
            return "evento/crear";
        }

        try {
            eventoService.crearBorrador(form);
            redirectAttributes.addFlashAttribute(
                    "aviso", "El evento se guardó como borrador."
            );
            return "redirect:/organizador/eventos/nuevo?exito";
        } catch (IllegalArgumentException e) {
            bindingResult.reject("evento", e.getMessage());
            prepararFormulario(model);
            return "evento/crear";
        }
    }

    private void prepararFormulario(Model model) {
        model.addAttribute("venues", venueRepository.findAllByOrderByNombreAsc());
        model.addAttribute("categorias", categoriaRepository.findAllByOrderByNombreAsc());
        model.addAttribute("menuActivo", "crear-evento");
    }
}
