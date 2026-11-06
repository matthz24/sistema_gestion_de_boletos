/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.datos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author josem
 */
@Getter
@Setter
public class TipoEntradaForm {

    @NotBlank(message = "Escriba el nombre de la categoría.")
    @Size(max = 60, message = "El nombre admite hasta 60 caracteres.")
    private String nombre;

    @NotNull(message = "Escriba el precio.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero.")
    private BigDecimal precio;

    @NotNull(message = "Escriba la cantidad disponible.")
    @Positive(message = "La cantidad debe ser mayor que cero.")
    private Integer cantidad;
}
