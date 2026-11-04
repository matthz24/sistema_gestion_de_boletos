/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author matth
 */
@Getter
@Setter
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String[] MESES = {"enero", "febrero", "marzo", "abril", "mayo", "junio",
        "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
    private static final String[] DIAS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes",
        "Sábado", "Domingo"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 200)
    private String subtitulo;

    @Column(nullable = false, length = 2000)
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "etiqueta_banner", length = 60)
    private String etiquetaBanner;

    @Column(name = "color_fondo", nullable = false, length = 9)
    private String colorFondo = "#1f2547";

    @Column(name = "color_circulo", nullable = false, length = 9)
    private String colorCirculo = "#dc5c8c";

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_venue")
    private Venue venue;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("precio DESC")
    private List<TipoEntrada> tiposEntrada = new ArrayList<>();

    public void agregarTipoEntrada(TipoEntrada tipo) {
        tipo.setEvento(this);
        tiposEntrada.add(tipo);
    }

    public String getFechaCorta() {
        return String.format("%02d %s · %02d:%02d", fechaHora.getDayOfMonth(), abreviatura().toUpperCase(Locale.ROOT),
                fechaHora.getHour(), fechaHora.getMinute());
    }

    public String getFechaBanner() {
        return fechaHora.getDayOfMonth() + " " + MESES[fechaHora.getMonthValue() - 1].toUpperCase(Locale.ROOT);
    }

    public String getFechaLarga() {
        int hora12 = fechaHora.getHour() % 12 == 0 ? 12 : fechaHora.getHour() % 12;
        String sufijo = fechaHora.getHour() < 12 ? "am" : "pm";
        return String.format("%s, %d %s · %d:%02d %s", DIAS[fechaHora.getDayOfWeek().getValue() - 1],
                fechaHora.getDayOfMonth(), abreviatura(), hora12, fechaHora.getMinute(), sufijo);
    }

    public String getResumenBanner() {
        return (getFechaBanner() + " · " + venue.getNombreCompleto()).toUpperCase(Locale.ROOT);
    }

    public String getEstiloFondo() {
        StringBuilder estilo = new StringBuilder("background-color:").append(colorFondo).append(";");
        if (imagenUrl != null && !imagenUrl.isBlank()) {
            String url = imagenUrl.replaceAll("[\"'()\\\\;]", "");
            estilo.append("background-image:linear-gradient(rgba(14,18,48,.35),rgba(14,18,48,.35)),url('")
                    .append(url).append("');background-size:cover;background-position:center;");
        }
        return estilo.toString();
    }

    private String abreviatura() {
        return MESES[fechaHora.getMonthValue() - 1].substring(0, 3);
    }
}
