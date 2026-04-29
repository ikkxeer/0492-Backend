/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;

import com.example.backend.domain.Ordre;
import com.example.backend.domain.Pale;
import java.util.ArrayList;
import java.util.List;

public class OrdreDTO {
    public Integer id_ordre;
    public String identificador;
    public String gestorResponsable;
    public Integer idGestor;
    public String dataCreacio;
    public String estat;
    public String adreca;
    public String ciutat;
    public String cp;
    public Double pesTotal;
    public Integer quantitatPales;
    public List<TrackingDTO> historial;
    public List<Integer> paleIds; 
    public String telefon;
    public String temporada;
    public String prioritat;
    public Double preu;
    public String tendaDestinataria;
    public String codiAlbara;
    public String transportista;

    public OrdreDTO() {}

    public OrdreDTO(Ordre o) {
        this.id_ordre = o.getId_ordre();
        this.identificador = o.getIdentificador();
        this.estat = o.getEstat();
        this.adreca = o.getAdreca();
        this.ciutat = o.getCiutat();
        this.cp = o.getCp();
        this.tendaDestinataria = o.getTendaDestinataria();
        this.codiAlbara = o.getCodiAlbara();
        this.telefon = o.getTelefon();
        this.temporada = o.getTemporada();
        this.prioritat = o.getPrioritat();
        this.preu = (o.getPreu() != null) ? o.getPreu().doubleValue() : 0.0;
        
        this.transportista = o.getTransportista() != null ? o.getTransportista().getNom() : null;
        
        if (o.getData_creacio() != null) {
            this.dataCreacio = o.getData_creacio().toString();
        }

        if (o.getGestor() != null) {
            this.gestorResponsable = o.getGestor().getNom();
            this.idGestor = o.getGestor().getId();
        }

        if (o.getPesTotal() != null) {
            this.pesTotal = o.getPesTotal().doubleValue();
        }

        this.paleIds = new ArrayList<>();
        if (o.getPales() != null) {
            for (Pale p : o.getPales()) {
                this.paleIds.add(p.getId_pale()); 
            }
            this.quantitatPales = o.getPales().size();
        }
    }
}