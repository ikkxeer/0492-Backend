/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;
import com.example.backend.domain.Tracking;

/**
 * DTO que representa el Tracking
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public class TrackingDTO {
    // Atributs de la classe
    public Integer id;
    public String etapa;
    public String ubicacio;
    public String timestamp;
    public String usuari;
    public String notes;
    
    /**
     * Constructor de Tracking
     * 
     * @param t Tracking base
     */
    public TrackingDTO(Tracking t) {
        // Omplim els atributs
        this.id = t.getId_tracking();
        this.etapa = t.getEtapa();
        this.ubicacio = t.getUbicacio();
        this.timestamp = t.getTimestamp().toString();
        this.notes = t.getNotes();

        // Si té usuari li possem el nom, sino 'Sistema'
        if (t.getUsuari() != null) {
            this.usuari = t.getUsuari().getNom();
        } else {
            this.usuari = "Sistema";
        }
    }
}
