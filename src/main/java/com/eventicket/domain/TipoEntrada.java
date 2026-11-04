/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eventicket.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author matth
 */
@Getter
@Setter
@Entity
@Table(name = "tipo_entrada")
public class TipoEntrada implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_entrada")
    private Long id;
 
    @Column(nullable = false, length = 60)
    private String nombre;
 
    @Column(length = 160)
    private String descripcion;
 
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_evento")
    private Evento evento;
}
 