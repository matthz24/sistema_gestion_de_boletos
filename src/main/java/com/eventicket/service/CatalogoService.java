/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.service;

import com.eventicket.domain.Categoria;
import com.eventicket.domain.Evento;
import com.eventicket.domain.Venue;
import com.eventicket.repository.CategoriaRepository;
import com.eventicket.repository.EventoRepository;
import com.eventicket.repository.VenueRepository;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author matth
 */
@Service
@RequiredArgsConstructor
public class CatalogoService {
 
    private static final String[] MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
 
    public record MesFiltro(String valor, String etiqueta) { }
 
    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;
    private final VenueRepository venueRepository;
 
    @Transactional(readOnly = true)
    public List<Evento> buscar(String texto, String mes, Long categoriaId, Long venueId) {
        String patron = "%" + (texto == null ? "" : texto.trim().toLowerCase(Locale.ROOT)) + "%";
        LocalDateTime desde = LocalDateTime.of(1900, 1, 1, 0, 0);
        LocalDateTime hasta = LocalDateTime.of(2999, 12, 31, 0, 0);
 
        YearMonth periodo = convertirMes(mes);
        if (periodo != null) {
            desde = periodo.atDay(1).atStartOfDay();
            hasta = periodo.plusMonths(1).atDay(1).atStartOfDay();
        }
 
        return eventoRepository.buscar(patron, desde, hasta,
                categoriaId == null ? 0L : categoriaId,
                venueId == null ? 0L : venueId);
    }
 
    @Transactional(readOnly = true)
    public Evento getEventoActivo(Long id) {
        return eventoRepository.findActivoConEntradas(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró ese evento o ya no está disponible."));
    }
 
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias() {
        return categoriaRepository.findAllByOrderByNombreAsc();
    }
 
    @Transactional(readOnly = true)
    public List<Venue> getVenues() {
        return venueRepository.findAllByOrderByNombreAsc();
    }
 
    @Transactional(readOnly = true)
    public List<MesFiltro> getMesesDisponibles() {
        Set<YearMonth> periodos = new LinkedHashSet<>();
        for (Evento evento : eventoRepository.findByActivoTrueOrderByFechaHoraAsc()) {
            periodos.add(YearMonth.from(evento.getFechaHora()));
        }
        return periodos.stream()
                .map(p -> new MesFiltro(p.toString(), MESES[p.getMonthValue() - 1] + " " + p.getYear()))
                .toList();
    }
 
    private YearMonth convertirMes(String mes) {
        if (mes == null || mes.isBlank()) {
            return null;
        }
        try {
            return YearMonth.parse(mes.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
 
