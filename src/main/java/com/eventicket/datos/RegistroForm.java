/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.datos;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author josem
 */
@Getter
@Setter
public class RegistroForm {
    @NotBlank(message = "Escriba su nombre")
    @Size(max = 80, message = "El nombre admite hasta 80 caracteres")
    private String nombre;
    
    @NotBlank(message = "Escriba su apellido")
    @Size(max = 80, message = "El apellido admite hasta 80 caracteres")
    private String apellidos;
    
    @NotBlank(message = "Escriba su correo")
    @Email(message = "Escriba un correo válido")
    @Size(max = 160, message = "El correo admite hasta 160 caracteres")
    private String correo;
    
    @NotBlank(message = "Escriba su contraseña")
    @Size(min = 8, max = 72, message = "La contraseña debe de tener entre 8 y 72 caracteres")
    private String contrasena;
    
    @NotBlank(message = "Repita la contraseña")
    private String confirmarContrasena;
    
    @AssertTrue(message = "Debe aceptar los términos y polítca de privacidad")
    private boolean aceptarTerminos;
}
