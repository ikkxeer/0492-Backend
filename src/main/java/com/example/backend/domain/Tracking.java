/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Taula 'tracking' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "tracking")
public class Tracking {
    
    // Constructor per defecte
    public Tracking() {}

    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tracking;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_ordre")
    private Ordre ordre;

    private String etapa;
    private String ubicacio;
    private LocalDateTime timestamp;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_usuari")
    private UserAccount usuari;

    @Column(columnDefinition = "TEXT")
    private String notes;

    // Getters
    public Integer getId_tracking() {
        return id_tracking;
    }

    public Ordre getOrdre() {
        return ordre;
    }

    public String getEtapa() {
        return etapa;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UserAccount getUsuari() {
        return usuari;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setId_tracking(Integer id_tracking) {
        this.id_tracking = id_tracking;
    }

    public void setOrdre(Ordre ordre) {
        this.ordre = ordre;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsuari(UserAccount usuari) {
        this.usuari = usuari;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}