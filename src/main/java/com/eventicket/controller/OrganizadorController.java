package com.eventicket.controller;

import com.eventicket.domain.SolicitudOrganizador;
import com.eventicket.service.SolicitudOrganizadorService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/organizador")
public class OrganizadorController {

    private final SolicitudOrganizadorService solicitudService;

    @GetMapping("/solicitud")
    public String solicitud(Model model) {
        model.addAttribute("solicitud", new SolicitudOrganizador());
        return "organizador/solicitud";
    }

    @PostMapping("/solicitud")
    public String guardar(@Valid @ModelAttribute("solicitud") SolicitudOrganizador solicitud,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "organizador/solicitud";
        }

        try {
            solicitudService.save(solicitud);
            redirectAttributes.addFlashAttribute("aviso", "Solicitud enviada. Quedó pendiente de revisión.");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("correo", "correo.duplicado", e.getMessage());
            return "organizador/solicitud";
        }

        return "redirect:/organizador/solicitud";
    }
}
