/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Taula 'incidencia_historial' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "incidencia_historial")
public class EntradaHistorial {
    
    // Constructor per defecte
    public EntradaHistorial() {}
    
    /**
     * Constructor parametritzat de la classe
     * 
     * @param accio Accio de la incidencia
     * @param descripcio Descripcio de la incidencia
     * @param autor Autor de la incidencia
     */
    public EntradaHistorial(String accio, String descripcio, String autor) {
        this.accio = accio;
        this.descripcio = descripcio;
        this.autor = autor;
        this.dataHora = LocalDateTime.now();
    }
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accio;
    private String descripcio;
    private LocalDateTime dataHora;
    private String autor;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "incidencia_id")
    private Incidencia incidencia;
    
    // Getters
    public Long getId() {
        return id;
    }

    public String getAccio() {
        return accio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getAutor() {
        return autor;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccio(String accio) {
        this.accio = accio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
      
}
