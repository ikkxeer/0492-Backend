package com.example.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * Taula 'gruppales' representada amb getters i setters
 * 
 * * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Entity
@Table(name = "gruppales")
public class GrupPales {

    // Constructor per defecte
    public GrupPales() {}

    // Columnes de la taula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grup_pales;

    @Column(name = "referencia", length = 50)
    private String referencia;

    @Column(name = "temporada", length = 20)
    private String temporada;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "estat", length = 30)
    private String estat;

    // Relació de que molts grups de pales pertanyen a un proveïdor
    @ManyToOne
    @JoinColumn(name = "id_proveidor", referencedColumnName = "id_client")
    private Client proveidor;

    // GETTERS
    public Integer getId_grup_pales() {
        return id_grup_pales;
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

    // SETTERS
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
}