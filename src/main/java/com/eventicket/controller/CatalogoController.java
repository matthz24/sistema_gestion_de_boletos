/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.controller;

import com.eventicket.service.CatalogoService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author matth
 */


@Controller
@RequiredArgsConstructor
@RequestMapping("/eventos")
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping
    public String catalogo(@RequestParam(name = "q", required = false) String texto,
            @RequestParam(name = "mes", required = false) String mes,
            @RequestParam(name = "categoria", required = false) Long categoria,
            @RequestParam(name = "venue", required = false) Long venue,
            Model model) {
        boolean hayFiltros = (texto != null && !texto.isBlank())
                || (mes != null && !mes.isBlank())
                || (categoria != null && categoria != 0)
                || (venue != null && venue != 0);

        model.addAttribute("eventos", catalogoService.buscar(texto, mes, categoria, venue));
        model.addAttribute("meses", catalogoService.getMesesDisponibles());
        model.addAttribute("categorias", catalogoService.getCategorias());
        model.addAttribute("venues", catalogoService.getVenues());
        model.addAttribute("texto", texto);
        model.addAttribute("mesSel", mes);
        model.addAttribute("categoriaSel", categoria);
        model.addAttribute("venueSel", venue);
        model.addAttribute("hayFiltros", hayFiltros);
        return "catalogo/catalogo";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable("id") Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("evento", catalogoService.getEventoActivo(id));
            return "catalogo/detalle";
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/eventos";
        }
    }
}
