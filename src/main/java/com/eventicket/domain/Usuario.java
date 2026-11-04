/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.domain;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author josem
 */
@Getter
@Setter
@Entity
@Table(name= "usuario")
public class Usuario implements Serializable{
    
    private static final long serialVersionUID= 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    private Long id;
    
    @Column(nullable = false, length = 80)
    private String nombre;
    
    @Column(nullable = false, length = 80)
    private String apellidos;
    
    @Column(nullable = false, unique = true, length = 160)
    private String correo;
    
    //esta funcion es para guardar con un hash BCrypt, nunca la contraseña en texto plano o quemada
    @Column(nullable = false, length = 100)
    private String contrasena;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private RolUsuario rol= RolUsuario.COMPRADOR;
    
    @Column(nullable = false)
    private boolean activo= true;
    
    //El comprador queda aprobado al registrarse, el organizador requiere admin approve
    @Column(nullable = false)
    private boolean aprobado= true;
    
    @Column(name= "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    @Column(name = "fecha_aceptacion_terminos")
    private LocalDateTime fechaAceptacionTerminos;
    
    public String getNombreCompleto(){
        return nombre + " "+ apellidos;
    }
    
}
