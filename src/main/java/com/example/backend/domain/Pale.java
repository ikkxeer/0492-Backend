/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author samui
 */
@Entity
@Table(name = "pale")
public class Pale {

    public Pale() {}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pale;

    // Relación con la tabla de grupos
    private Integer id_grup_pales; 
    private String lot;
    private String sscc;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Double pes;
    private String mesures;
    private Integer paquets;
    private LocalDateTime data_expedicio;
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
}