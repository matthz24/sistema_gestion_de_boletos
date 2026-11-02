package com.eventicket.service;

import com.eventicket.domain.EstadoSolicitud;
import com.eventicket.domain.SolicitudOrganizador;
import com.eventicket.repository.SolicitudOrganizadorRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SolicitudOrganizadorService {

    private final SolicitudOrganizadorRepository solicitudRepository;

    // Registra la solicitud como pendiente para que la revise administración.
    @Transactional
    public void save(SolicitudOrganizador solicitud) {
        String correo = solicitud.getCorreo().trim().toLowerCase(Locale.ROOT);
        if (solicitudRepository.existsByCorreoIgnoreCase(correo)) {
            throw new IllegalArgumentException("Ya hay una solicitud registrada con ese correo.");
        }

        // El formulario crea una solicitud nueva; nunca modifica una existente.
        solicitud.setId(null);
        solicitud.setCorreo(correo);
        solicitud.setNombreOrganizacion(solicitud.getNombreOrganizacion().trim());
        solicitud.setNombreContacto(solicitud.getNombreContacto().trim());
        solicitud.setTelefono(solicitud.getTelefono() == null ? null : solicitud.getTelefono().trim());
        solicitud.setIdentificacionLegal(solicitud.getIdentificacionLegal().trim());
        solicitud.setSitioWeb(solicitud.getSitioWeb() == null || solicitud.getSitioWeb().isBlank()
                ? null : solicitud.getSitioWeb().trim());
        solicitud.setTipoEventos(solicitud.getTipoEventos().trim());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaSolicitud(LocalDateTime.now());
        solicitud.setMotivoRechazo(null);
        solicitudRepository.save(solicitud);
    }

    @Transactional(readOnly = true)
    public List<SolicitudOrganizador> getSolicitudesPendientes() {
        return solicitudRepository.findByEstadoOrderByFechaSolicitudAsc(EstadoSolicitud.PENDIENTE);
    }

    @Transactional(readOnly = true)
    public long getTotalSolicitudes(EstadoSolicitud estado) {
        return solicitudRepository.countByEstado(estado);
    }

    @Transactional
    public void aprobar(Long idSolicitud) {
        SolicitudOrganizador solicitud = getSolicitudPendiente(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitud.setMotivoRechazo(null);
    }

    @Transactional
    public void rechazar(Long idSolicitud, String motivoRechazo) {
        if (motivoRechazo == null || motivoRechazo.isBlank()) {
            throw new IllegalArgumentException("Escriba el motivo del rechazo.");
        }
        SolicitudOrganizador solicitud = getSolicitudPendiente(idSolicitud);
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        solicitud.setMotivoRechazo(motivoRechazo.trim());
    }

    private SolicitudOrganizador getSolicitudPendiente(Long idSolicitud) {
        SolicitudOrganizador solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró esa solicitud."));
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException("Esa solicitud ya había sido revisada.");
        }
        return solicitud;
    }
}
