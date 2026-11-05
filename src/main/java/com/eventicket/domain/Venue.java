/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author matth
 */
@Getter
@Setter
@Entity
@Table(name = "venue")
public class Venue implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venue")
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(length = 200)
    private String direccion;

    @Column(length = 80)
    private String ciudad;

    @Column(nullable = false, length = 60)
    private String pais = "Costa Rica";

    @Column(nullable = false)
    private Integer capacidad = 100;

    public String getNombreCompleto() {
        return ciudad == null || ciudad.isBlank()
                ? nombre
                : nombre + " · " + ciudad;
    }

    public String getUbicacionCompleta() {
        return getNombreCompleto() + ", " + pais;
    }
}
 