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
 * Taula 'client' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "client")
public class Client {
    
    // Constructor per defecte
    public Client () {}
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_client;
    @Column(name = "nom")
    private String nom;
    @Column(name = "telefon")
    private String telefon;
    
    // Getters
    public Integer getId_client() {
        return id_client;
    }

    public String getNom() {
        return nom;
    }

    public String getTelefon() {
        return telefon;
    }
    
    // Setters
    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    
}
