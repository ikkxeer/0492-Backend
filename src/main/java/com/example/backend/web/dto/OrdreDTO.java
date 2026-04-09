/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;
import com.example.backend.domain.Ordre;
import com.example.backend.domain.Tracking;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO que representa una Ordre
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public class OrdreDTO {
    // Atributs de la classe
    public Integer id_ordre;
    public String identificador;
    public String gestorResponsable;
    public String dataCreacio;
    public String estat;
    public String adreca;
    public String ciutat;
    public String cp;
    public Double pesTotal;
    public Integer quantitatPales;
    public List<TrackingDTO> historial;

    /**
     * Constructor de Ordre
     * 
     * @param o Ordre base
     */
    public OrdreDTO(Ordre o) {
        // Omplim els atributs
        this.id_ordre = o.getId_ordre();
        this.identificador = o.getIdentificador();
        this.dataCreacio = o.getData_creacio().toString();
        this.estat = o.getEstat();
        this.adreca = o.getAdreca();
        this.ciutat = o.getCiutat();
        this.cp = o.getCp();
        this.quantitatPales = o.getQuantitatPales();

        // Si el gestor no es null omplim amb el nom, sino amb "Sense Gestor"
        if (o.getGestor() != null) {
            this.gestorResponsable = o.getGestor().getNom();
        } else {
            this.gestorResponsable = "Sense gestor";
        }

        // Si té pes, el possem, sino possem 0.0
        if (o.getPesTotal() != null) {
            this.pesTotal = o.getPesTotal().doubleValue();
        } else {
            this.pesTotal = 0.0;
        }

        // Si té historial el possem
        this.historial = new ArrayList<>();
        if (o.getHistorial() != null) {
            for (Tracking t : o.getHistorial()) {
                this.historial.add(new TrackingDTO(t));
            }
        }
    }
}