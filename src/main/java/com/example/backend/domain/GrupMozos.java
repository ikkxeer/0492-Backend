/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Taula 'grupmozos' representada amb getters i setters
 * @author samui
 */
@Entity
@Table(name = "grupmozos")
public class GrupMozos {
    
    // Constructor per defecte
    public GrupMozos () {}
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grup;
    @Column(name = "nom")
    private String nom;
    @Column(name = "descripcio")
    private String descripcio;
    
    // Getters
    public Integer getId_grup() {
        return id_grup;
    }

    public String getNom() {
        return nom;
    }

    public String getDescripcio() {
        return descripcio;
    }
    
    // Setters
    public void setId_grup(Integer id_grup) {
        this.id_grup = id_grup;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }
    

}
