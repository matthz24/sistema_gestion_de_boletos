package com.eventicket.controller;

import com.eventicket.domain.EstadoSolicitud;
import com.eventicket.service.SolicitudOrganizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/organizadores")
public class AdministradorOrganizadorController {

    private final SolicitudOrganizadorService solicitudService;

    @GetMapping("/pendientes")
    public String listado(Model model) {
        model.addAttribute("solicitudes", solicitudService.getSolicitudesPendientes());
        model.addAttribute("pendientesCount", solicitudService.getTotalSolicitudes(EstadoSolicitud.PENDIENTE));
        model.addAttribute("aprobadasCount", solicitudService.getTotalSolicitudes(EstadoSolicitud.APROBADA));
        model.addAttribute("rechazadasCount", solicitudService.getTotalSolicitudes(EstadoSolicitud.RECHAZADA));
        return "organizador/pendientes";
    }

    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable("id") Long id,
                          RedirectAttributes redirectAttributes) {
        try {
            solicitudService.aprobar(id);
            redirectAttributes.addFlashAttribute("aviso", "La solicitud fue aprobada.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/organizadores/pendientes";
    }

    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable("id") Long id,
                           @RequestParam("motivo") String motivo,
                           RedirectAttributes redirectAttributes) {
        try {
            solicitudService.rechazar(id, motivo);
            redirectAttributes.addFlashAttribute("aviso", "La solicitud fue rechazada.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/organizadores/pendientes";
    }
}
