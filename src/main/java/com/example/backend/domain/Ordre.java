/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Taula 'ordre' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "ordre")
public class Ordre {
    
    // Constructor per defecte
    public Ordre() {}

    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ordre;
    
    @Column(length = 50)
    private String identificador;
    
    @ManyToOne
    @JoinColumn(name = "id_gestor")
    private UserAccount gestor;
    
    private LocalDateTime data_creacio;
    
    @Column(name = "estat", length = 30)
    private String estat;

    private String adreca;
    private String ciutat;
    private String cp;

    @Column(name = "pes_total", precision = 10, scale = 2)
    private BigDecimal pesTotal;

    @Column(name = "quantitat_pales")
    private Integer quantitatPales;

    @OneToMany(mappedBy = "ordre", cascade = CascadeType.ALL)
    @OrderBy("timestamp ASC")
    private List<Tracking> historial;

    // GETTERS
    public Integer getId_ordre() {
        return id_ordre;
    }

    public String getIdentificador() {
        return identificador;
    }

    public UserAccount getGestor() {
        return gestor;
    }

    public LocalDateTime getData_creacio() {
        return data_creacio;
    }

    public String getEstat() {
        return estat;
    }

    public String getAdreca() {
        return adreca;
    }

    public String getCiutat() {
        return ciutat;
    }

    public String getCp() {
        return cp;
    }

    public BigDecimal getPesTotal() {
        return pesTotal;
    }

    public Integer getQuantitatPales() {
        return quantitatPales;
    }

    public List<Tracking> getHistorial() {
        return historial;
    }

    // SETTERS
    public void setId_ordre(Integer id_ordre) {
        this.id_ordre = id_ordre;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public void setGestor(UserAccount gestor) {
        this.gestor = gestor;
    }

    public void setData_creacio(LocalDateTime data_creacio) {
        this.data_creacio = data_creacio;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setPesTotal(BigDecimal pesTotal) {
        this.pesTotal = pesTotal;
    }

    public void setQuantitatPales(Integer quantitatPales) {
        this.quantitatPales = quantitatPales;
    }

    public void setHistorial(List<Tracking> historial) {
        this.historial = historial;
    }
    
}