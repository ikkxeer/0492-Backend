/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Taula 'pale' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "pale")
public class Pale {

    // Constructor per defecte
    public Pale() {}
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pale;
    @Column(name = "id_grup_pales")
    private Integer id_grup_pales; 
    @Column(name = "lot")
    private String lot;
    @Column(name = "sscc")
    private String sscc;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double pes;
    @Column(name = "mesures")
    private String mesures;
    @Column(name = "paquets")
    private Integer paquets;
    @Column(name = "data_expedicio")
    private LocalDateTime data_expedicio;
    @Column(name = "estat")
    private String estat;
    
    // Getters
    public Integer getId_pale() { return id_pale; }
    public Integer getId_grup_pales() { return id_grup_pales; }
    public String getLot() { return lot; }
    public String getSscc() { return sscc; }
    public Double getPes() { return pes; }
    public String getMesures() { return mesures; }
    public Integer getPaquets() { return paquets; }
    public LocalDateTime getData_expedicio() { return data_expedicio; }
    public String getEstat() { return estat; }

    // Setters
    public void setId_grup_pales(Integer id_grup_pales) { this.id_grup_pales = id_grup_pales; }
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

    public void setPes(Double pes) {
        this.pes = pes;
    }

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