package com.example.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    
    @ManyToMany
    @JoinTable(
        name = "grupmozos_usuaris",
        joinColumns = @JoinColumn(name = "id_usuari"),
        inverseJoinColumns = @JoinColumn(name = "id_grup")
    )
    private List<GrupMozos> grups;
    
    @OneToMany(mappedBy = "usuari", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tracking> trackings;

    @OneToMany(mappedBy = "gestor", cascade = CascadeType.ALL)
    private List<Ordre> ordresGestionades;
    
    // Getters
    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public String getDepartament() {
        return departament;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public List<GrupMozos> getGrups() {
        return grups;
    }

    public List<Tracking> getTrackings() {
        return trackings;
    }

    public List<Ordre> getOrdresGestionades() {    
        return ordresGestionades;
    }

    public boolean isEstat() {
        return estat;
    }

    public LocalDateTime getUltimAcces() {
        return ultimAcces;
    }
    
    // Setters
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public void setEstat(boolean estat) {
        this.estat = estat;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public void setUltimAcces(LocalDateTime ultimAcces) {
        this.ultimAcces = ultimAcces;
    }
    
}