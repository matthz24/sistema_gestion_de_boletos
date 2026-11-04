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
@Table(name= "token_recuperacion")
public class TokenRecuperacion implements Serializable{
     
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long id;
    
    //un hash hexadecimal de 64 caracteres. El token plano nunca se guarda
    @Column(name="token_hash", nullable = false, unique = true, length = 64)
    private String tokenHash;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @Column(name = "expira_en", nullable = false)
    private LocalDateTime expiraEn;
    
    @Column(nullable = false)
    private boolean usado= false;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    public boolean esVigente(LocalDateTime ahora){
        return !usado && expiraEn.isAfter(ahora);
    }
}
