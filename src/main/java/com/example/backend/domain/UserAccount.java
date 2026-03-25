package com.example.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "usuari")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nom;
    private String email;
    private String telefon;
    private String contrasenya;
    private String departament;
    private String ubicacio;

    @Column(name = "id_rol")
    private Integer idRol;

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getContrasenya() { return contrasenya; }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    

    public java.util.List<String> getRoles() {
        if (idRol == 1) return java.util.List.of("ROLE_USER");
        if (idRol == 2) return java.util.List.of("ROLE_ADMIN");
        return java.util.List.of();
    }
}