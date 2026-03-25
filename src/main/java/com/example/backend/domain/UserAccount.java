package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "usuari")
public class UserAccount {

    public UserAccount() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom")
    private String nom;
    @Column(name = "email")
    private String email;
    private String telefon;
    private String contrasenya;
    private String departament;
    private String ubicacio;

    @Column(name = "id_rol")
    private Integer idRol;

    // GETERS
    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getContrasenya() { return contrasenya; }
    public String getTelefon() { return telefon; }
    public String getDepartament() { return departament; }
    public String getUbicacio() { return ubicacio; }
    public Integer getIdRol() { return idRol; }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    

    public java.util.List<String> getRoles() {
        if (idRol == 1) return java.util.List.of("ROL_ADMINISTRADOR");
        if (idRol == 2) return java.util.List.of("ROL_GESTOR");
        if (idRol == 3) return java.util.List.of("ROL_MOZO");
        if (idRol == 4) return java.util.List.of("ROL_REPARTIDOR");

        return java.util.List.of();
    }
}