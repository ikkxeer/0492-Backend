/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Taula 'pale' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "pale")
public class Pale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id") // El front espera "id"
    private Integer id_pale;

    @ManyToOne
    @JoinColumn(name = "id_grup_pales")
    @JsonBackReference
    private GrupPales grupPales;

    private String lot;
    private String sscc;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private java.math.BigDecimal pes;

    private String mesures;
    private Integer paquets;
    
    @JsonProperty("dataExpedicio") // Mapeo de data_expedicio a dataExpedicio
    @Column(name = "data_expedicio")
    private LocalDateTime data_expedicio;
    
    private String estat;
    
    // Getters
    public Integer getId_pale() { return id_pale; }
    public String getLot() { return lot; }
    public String getSscc() { return sscc; }
    public java.math.BigDecimal getPes() { return pes; }
    public String getMesures() { return mesures; }
    public Integer getPaquets() { return paquets; }
    public LocalDateTime getData_expedicio() { return data_expedicio; }
    public String getEstat() { return estat; }

    // Setters
    public void setEstat(String estat) { this.estat = estat; }

    public void setId_pale(Integer id_pale) {
        this.id_pale = id_pale;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public void setSscc(String sscc) {
        this.sscc = sscc;
    }

    public void setPes(java.math.BigDecimal pes) { this.pes = pes; }

    public void setMesures(String mesures) {
        this.mesures = mesures;
    }

    public void setPaquets(Integer paquets) {
        this.paquets = paquets;
    }

    public void setData_expedicio(LocalDateTime data_expedicio) {
        this.data_expedicio = data_expedicio;
    }
    
}