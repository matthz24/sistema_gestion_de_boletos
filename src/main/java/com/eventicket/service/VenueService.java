/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.service;

import com.eventicket.datos.VenueForm;
import com.eventicket.domain.Venue;
import com.eventicket.repository.EventoRepository;
import com.eventicket.repository.VenueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author josem
 */
@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final EventoRepository eventoRepository;

    @Transactional(readOnly = true)
    public List<Venue> listar() {
        return venueRepository.findAllByOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public VenueForm obtenerFormulario(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(()
                        -> new IllegalArgumentException("No se encontró el recinto."));

        VenueForm form = new VenueForm();
        form.setId(venue.getId());
        form.setNombre(venue.getNombre());
        form.setUbicacion(
                venue.getCiudad() == null || venue.getCiudad().isBlank()
                ? venue.getDireccion()
                : venue.getCiudad()
        );
        form.setCapacidad(venue.getCapacidad());

        return form;
    }

    @Transactional
    public void guardar(VenueForm form) {
        Venue venue;

        if (form.getId() == null) {
            venue = new Venue();
        } else {
            venue = venueRepository.findById(form.getId())
                    .orElseThrow(()
                            -> new IllegalArgumentException("No se encontró el recinto."));
        }

        venue.setNombre(form.getNombre().trim());
        venue.setCiudad(form.getUbicacion().trim());
        venue.setCapacidad(form.getCapacidad());

        venueRepository.save(venue);
    }

    @Transactional
    public void eliminar(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(()
                        -> new IllegalArgumentException("No se encontró el recinto."));

        if (eventoRepository.existsByVenue_Id(id)) {
            throw new IllegalStateException(
                    "No se puede eliminar este recinto porque tiene eventos asociados."
            );
        }

        venueRepository.delete(venue);
    }
}
