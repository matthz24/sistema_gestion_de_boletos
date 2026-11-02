package com.eventicket.repository;

import com.eventicket.domain.EstadoSolicitud;
import com.eventicket.domain.SolicitudOrganizador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudOrganizadorRepository extends JpaRepository<SolicitudOrganizador, Long> {

    // Consulta derivada: obtiene las solicitudes de un estado en orden de llegada.
    List<SolicitudOrganizador> findByEstadoOrderByFechaSolicitudAsc(EstadoSolicitud estado);

    // Consulta derivada: evita registrar dos solicitudes con el mismo correo.
    boolean existsByCorreoIgnoreCase(String correo);

    // Cuenta las solicitudes para el resumen administrativo.
    long countByEstado(EstadoSolicitud estado);
}
