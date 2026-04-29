/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * Entitat que representa un albarà generat en confirmar una ordre.
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "albara")
public class Albara {

    public Albara() {}

    // Columnes de la base de dades
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_albara;

    @Column(unique = true, length = 30)
    private String codi;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "id_ordre")
    private Ordre ordre;

    @Column(name = "data_generacio")
    private LocalDateTime dataGeneracio;

    @Column(name = "preu_total", precision = 10, scale = 2)
    private BigDecimal preuTotal;

    // Getters
    public Integer getId_albara() {
        return id_albara;
    }

    public String getCodi() {
        return codi;
    }

    public Ordre getOrdre() {
        return ordre;
    }

    public LocalDateTime getDataGeneracio() {
        return dataGeneracio;
    }

    public BigDecimal getPreuTotal() {
        return preuTotal;
    }
    
    // Setters
    public void setId_albara(Integer id_albara) {
        this.id_albara = id_albara;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public void setOrdre(Ordre ordre) {
        this.ordre = ordre;
    }

    public void setDataGeneracio(LocalDateTime dataGeneracio) {
        this.dataGeneracio = dataGeneracio;
    }

    public void setPreuTotal(BigDecimal preuTotal) {
        this.preuTotal = preuTotal;
    }

}
