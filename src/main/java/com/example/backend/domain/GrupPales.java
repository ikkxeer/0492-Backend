package com.example.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Taula 'gruppales' representada amb getters i setters
 * 
 * * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "gruppales")
public class GrupPales {

    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id_grup_pales;

    private String referencia;
    private String temporada;
    
    @JsonProperty("dataEntrada")
    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    private String estat;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_proveidor", referencedColumnName = "id_client")
    private Client proveidor; 

    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "grupPales", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Pale> pales;

    // Getters
    public Integer getId_grup_pales() {
        return id_grup_pales;
    }
    
    public List<Pale> getPales() { 
        return pales; 
    }

    public String getReferencia() {
        return referencia;
    }

    public String getTemporada() {
        return temporada;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public String getEstat() {
        return estat;
    }

    public Client getProveidor() {
        return proveidor;
    }

    // Setters
    public void setId_grup_pales(Integer id_grup_pales) {
        this.id_grup_pales = id_grup_pales;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public void setProveidor(Client proveidor) {
        this.proveidor = proveidor;
    }
    
    public void setPales(List<Pale> pales) { 
        this.pales = pales; 
    }
}