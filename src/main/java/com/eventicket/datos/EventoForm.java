/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.datos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josem
 */
@Getter
@Setter
public class EventoForm {

    @NotBlank(message = "Escriba el nombre del evento.")
    @Size(max = 150, message = "El nombre admite hasta 150 caracteres.")
    private String titulo;

    @NotBlank(message = "Escriba la descripción del evento.")
    @Size(max = 2000, message = "La descripción admite hasta 2000 caracteres.")
    private String descripcion;

    @NotNull(message = "Seleccione la fecha del evento.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @NotNull(message = "Seleccione la hora del evento.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime hora;

    @NotNull(message = "Seleccione un venue.")
    private Long venueId;

    @NotNull(message = "Seleccione una categoría.")
    private Long categoriaId;

    @NotEmpty(message = "Agregue al menos una categoría de boletos.")
    @Valid
    private List<TipoEntradaForm> tiposEntrada
            = new ArrayList<>(List.of(new TipoEntradaForm()));
}
