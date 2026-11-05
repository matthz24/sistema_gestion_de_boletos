/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.datos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author josem
 */
@Getter
@Setter
public class VenueForm {
    
    private Long id;
    
    @NotBlank(message = "Escriba el nombre del recinto.")
    @Size(max = 120, message = "El nombre admite hasta 120 caracteres.")
    private String nombre;
    
    @NotBlank(message = "Escriba la ubicación.")
    @Size(max = 80, message = "La ubicación admite hasta 80 caracteres.")
    private String ubicacion;
    
    @NotNull(message = "Escriba la capacidad.")
    @Positive(message = "La capacidad debe ser mayor que cero.")
    private Integer capacidad = 100;
    
}
