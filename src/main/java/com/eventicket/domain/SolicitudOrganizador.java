package com.eventicket.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "solicitud_organizador")
public class SolicitudOrganizador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long id;

    @Column(name = "nombre_organizacion", nullable = false, length = 120)
    @NotBlank(message = "Escriba el nombre comercial de la organización.")
    @Size(max = 120)
    private String nombreOrganizacion;

    @Column(name = "nombre_contacto", nullable = false, length = 120)
    @NotBlank(message = "Escriba el nombre de la persona de contacto.")
    @Size(max = 120)
    private String nombreContacto;

    @Column(nullable = false, unique = true, length = 160)
    @NotBlank(message = "Escriba un correo de contacto.")
    @Email(message = "Escriba un correo válido.")
    @Size(max = 160)
    private String correo;

    @Column(length = 30)
    @Size(max = 30)
    private String telefono;

    @Column(name = "identificacion_legal", length = 40)
    @NotBlank(message = "Escriba la identificación legal o cédula.")
    @Size(max = 40)
    private String identificacionLegal;

    @Column(name = "sitio_web", length = 180)
    @Size(max = 180)
    private String sitioWeb;

    // Se reutiliza la columna descripcion que ya existe en la base H2 local.
    @Column(name = "descripcion", nullable = false, length = 2000)
    @NotBlank(message = "Indique qué tipo de eventos organiza.")
    @Size(max = 180)
    private String tipoEventos;

    @Transient
    @AssertTrue(message = "Confirme que la información de la organización es correcta.")
    private boolean informacionCorrecta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    @Column(name = "motivo_rechazo", length = 1000)
    private String motivoRechazo;
}
