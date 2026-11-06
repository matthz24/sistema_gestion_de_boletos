/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.config;

import com.eventicket.domain.Categoria;
import com.eventicket.domain.Evento;
import com.eventicket.domain.TipoEntrada;
import com.eventicket.domain.Venue;
import com.eventicket.repository.CategoriaRepository;
import com.eventicket.repository.EventoRepository;
import com.eventicket.repository.VenueRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author matth
 */
// Datos de prueba del catálogo (los mismos eventos del diseño). Solo se cargan si no hay eventos.
@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogoDatosIniciales implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final VenueRepository venueRepository;
    private final EventoRepository eventoRepository;
    private final TransactionTemplate transactionTemplate;

    // Todo se guarda en una sola transacción: si algo falla no queda nada a medias
    @Override
    public void run(String... args) {
        try {
            transactionTemplate.executeWithoutResult(status -> cargarDatos());
        } catch (RuntimeException e) {
            log.error("No se pudieron cargar los datos", e);
        }
    }

    private void cargarDatos() {
        if (eventoRepository.count() > 0) {
            return;
        }

        Categoria musica = categoria("Música");
        Categoria conferencia = categoria("Conferencia");
        Categoria enVivo = categoria("En vivo");
        Categoria gastronomia = categoria("Gastronomía");

        Venue parqueViva = venue("Parque Viva", "La Guácima, Alajuela", "Alajuela");
        Venue convenciones = venue("Centro de Convenciones", "San José, Costa Rica", null);
        Venue terraza = venue("Terraza Nómado", "Barrio Escalante, San José", "San José");
        Venue mercado = venue("Mercado La California", "Barrio California, San José", null);

        Evento bruma = evento("Festival Bruma", "Una noche para volver a sentir la música en comunidad.",
                "Una celebración de la música alternativa bajo el cielo abierto. Tres escenarios, artistas locales "
                + "e internacionales, experiencias gastronómicas y espacios para descubrir sonidos nuevos.",
                LocalDateTime.of(2026, 10, 12, 19, 0), "Música en vivo", "#1f2547", "#dc5c8c", musica, parqueViva);
        entrada(bruma, "VIP", "Acceso preferencial + lounge", "42000");
        entrada(bruma, "General", "Acceso a todos los escenarios", "24000");

        Evento ideas = evento("Ideas que mueven", "Charlas cortas para cambiar la forma de ver tu ciudad.",
                "Una jornada con speakers de tecnología, diseño y emprendimiento. Charlas de 20 minutos, talleres "
                + "prácticos y espacios para conocer a otras personas con ganas de crear.",
                LocalDateTime.of(2026, 10, 18, 9, 30), "Conferencia", "#6b4fd8", "#e8b866", conferencia, convenciones);
        entrada(ideas, "Pase completo", "Charlas, talleres y almuerzo", "35000");
        entrada(ideas, "Pase de un día", "Acceso a la jornada principal", "18000");

        Evento jazz = evento("Jazz bajo las estrellas", "Música en vivo al aire libre, con la ciudad de fondo.",
                "Una noche íntima de jazz en una terraza con vista a la ciudad. Tres bandas locales, cócteles de "
                + "autor y un ambiente pensado para escuchar con calma.",
                LocalDateTime.of(2026, 10, 25, 20, 0), "Jazz en vivo", "#1f4260", "#4aa0b5", enVivo, terraza);
        entrada(jazz, "Mesa preferencial", "Mesa para dos cerca del escenario", "30000");
        entrada(jazz, "General", "Acceso a la terraza", "15000");

        Evento sabores = evento("Sabores de Costa Rica", "Un recorrido por la cocina que nos identifica.",
                "Cocineras tradicionales, chefs jóvenes y productores locales reunidos en un mismo mercado. "
                + "Degustaciones, demostraciones en vivo y productos para llevar a casa.",
                LocalDateTime.of(2026, 11, 2, 12, 0), "Gastronomía", "#614030", "#e8b45a", gastronomia, mercado);
        entrada(sabores, "Pasaporte degustación", "Incluye 8 degustaciones", "20000");
        entrada(sabores, "Entrada general", "Acceso al mercado", "8000");

        eventoRepository.save(bruma);
        eventoRepository.save(ideas);
        eventoRepository.save(jazz);
        eventoRepository.save(sabores);
    }

    private Categoria categoria(String nombre) {
        return categoriaRepository.findByNombre(nombre).orElseGet(() -> {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            return categoriaRepository.save(categoria);
        });
    }

    private Venue venue(String nombre, String direccion, String ciudad) {
        return venueRepository.findFirstByNombre(nombre).orElseGet(() -> {
            Venue venue = new Venue();
            venue.setNombre(nombre);
            venue.setDireccion(direccion);
            venue.setCiudad(ciudad);
            return venueRepository.save(venue);
        });
    }

    private Evento evento(String titulo, String subtitulo, String descripcion, LocalDateTime fechaHora,
            String etiqueta, String colorFondo, String colorCirculo,
            Categoria categoria, Venue venue) {
        Evento evento = new Evento();
        evento.setTitulo(titulo);
        evento.setSubtitulo(subtitulo);
        evento.setDescripcion(descripcion);
        evento.setFechaHora(fechaHora);
        evento.setEtiquetaBanner(etiqueta);
        evento.setColorFondo(colorFondo);
        evento.setColorCirculo(colorCirculo);
        evento.setCategoria(categoria);
        evento.setVenue(venue);
        evento.setActivo(true);
        return evento;
    }

    private void entrada(Evento evento, String nombre, String descripcion, String precio) {
        TipoEntrada tipo = new TipoEntrada();
        tipo.setNombre(nombre);
        tipo.setDescripcion(descripcion);
        tipo.setCantidad(50);
        tipo.setPrecio(new BigDecimal(precio));
        evento.agregarTipoEntrada(tipo);
    }
}
