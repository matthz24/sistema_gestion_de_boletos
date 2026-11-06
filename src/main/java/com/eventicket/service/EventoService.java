/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.service;

import com.eventicket.datos.EventoForm;
import com.eventicket.domain.Categoria;
import com.eventicket.domain.Evento;
import com.eventicket.domain.Venue;
import com.eventicket.repository.CategoriaRepository;
import com.eventicket.repository.EventoRepository;
import com.eventicket.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventicket.domain.TipoEntrada;

/**
 *
 * @author josem
 */
@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final CategoriaRepository categoriaRepository;
    private final VenueRepository venueRepository;

    @Transactional
    public void crearBorrador(EventoForm form) {
        Categoria categoria = categoriaRepository.findById(form.getCategoriaId())
                .orElseThrow(()
                        -> new IllegalArgumentException("La categoría seleccionada no existe."));

        Venue venue = venueRepository.findById(form.getVenueId())
                .orElseThrow(()
                        -> new IllegalArgumentException("El venue seleccionado no existe."));

        Evento evento = new Evento();
        evento.setTitulo(form.getTitulo().trim());
        evento.setDescripcion(form.getDescripcion().trim());
        evento.setFechaHora(form.getFecha().atTime(form.getHora()));
        evento.setCategoria(categoria);
        evento.setVenue(venue);
        evento.setActivo(false);

        long totalBoletos = form.getTiposEntrada().stream()
                .mapToLong(tipo -> tipo.getCantidad())
                .sum();

        if (totalBoletos > venue.getCapacidad()) {
            throw new IllegalArgumentException(
                    "La cantidad total de boletos no puede superar el aforo del venue."
            );
        }

        for (var tipoForm : form.getTiposEntrada()) {
            TipoEntrada tipoEntrada = new TipoEntrada();
            tipoEntrada.setNombre(tipoForm.getNombre().trim());
            tipoEntrada.setPrecio(tipoForm.getPrecio());
            tipoEntrada.setCantidad(tipoForm.getCantidad());

            evento.agregarTipoEntrada(tipoEntrada);
        }

        eventoRepository.save(evento);
    }
}
