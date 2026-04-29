/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import com.example.backend.domain.enums.TipusIncidencia;
import com.example.backend.domain.enums.PrioritatIncidencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Taula 'incidencia' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "incidencia")
public class Incidencia {
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_incidencia;

    @Column(name = "id_ordre")
    private Integer idOrdre;

    private String titol;
    
    @Column(columnDefinition = "TEXT")
    private String descripcio;

    private String estat;

    @Enumerated(EnumType.STRING)
    private PrioritatIncidencia prioritat;

    @Enumerated(EnumType.STRING)
    private TipusIncidencia tipus;

    @Column(name = "reportat_per")
    private Integer reportatPer;

    @Column(name = "assignat_a")
    private Integer assignatA;

    @Column(name = "data_creacio")
    private LocalDateTime dataCreacio;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "incidencia_id")
    private List<EntradaHistorial> historial = new ArrayList<>();

    // Getters
    public Integer getId_incidencia() {
        return id_incidencia;
    }

    public Integer getIdOrdre() {
        return idOrdre;
    }

    public String getTitol() {
        return titol;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getEstat() {
        return estat;
    }

    public PrioritatIncidencia getPrioritat() {
        return prioritat;
    }

    public TipusIncidencia getTipus() {
        return tipus;
    }

    public Integer getReportatPer() {
        return reportatPer;
    }

    public Integer getAssignatA() {
        return assignatA;
    }

    public LocalDateTime getDataCreacio() {
        return dataCreacio;
    }

    public List<EntradaHistorial> getHistorial() {
        return historial;
    }

    // Setters
    public void setId_incidencia(Integer id_incidencia) {
        this.id_incidencia = id_incidencia;
    }

    public void setIdOrdre(Integer idOrdre) {
        this.idOrdre = idOrdre;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public void setPrioritat(PrioritatIncidencia prioritat) {
        this.prioritat = prioritat;
    }

    public void setTipus(TipusIncidencia tipus) {
        this.tipus = tipus;
    }

    public void setReportatPer(Integer reportatPer) {
        this.reportatPer = reportatPer;
    }

    public void setAssignatA(Integer assignatA) {
        this.assignatA = assignatA;
    }

    public void setDataCreacio(LocalDateTime dataCreacio) {
        this.dataCreacio = dataCreacio;
    }

    public void setHistorial(List<EntradaHistorial> historial) {
        this.historial = historial;
    }
    
}