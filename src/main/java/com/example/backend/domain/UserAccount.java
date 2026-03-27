package com.example.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Taula 'usauri' representada amb getters i setters
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "usuari")
public class UserAccount {

    // Constructor per defecte
    public UserAccount() {
    }
    
    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom")
    private String nom;
    @Column(name = "email")
    private String email;
    @Column(name = "telefon")
    private String telefon;
    @Column(name = "contrasenya")
    private String contrasenya;
    @Column(name = "departament")
    private String departament;
    @Column(name = "ubicacio")
    private String ubicacio;
    @Column(name = "id_rol")
    private Integer idRol;
    @Column(name = "estat")
    private boolean estat;
    @Column(name = "ultim_acces")
    private LocalDateTime ultimAcces;

    // GETERS
    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getContrasenya() { return contrasenya; }
    public String getTelefon() { return telefon; }
    public String getDepartament() { return departament; }
    public String getUbicacio() { return ubicacio; }
    public Integer getIdRol() { return idRol; }

    public boolean isEstat() {
        return estat;
    }

    public LocalDateTime getUltimAcces() {
        return ultimAcces;
    }
    

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    
}