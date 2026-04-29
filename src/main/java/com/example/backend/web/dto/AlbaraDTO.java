/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;
import com.example.backend.domain.Albara;

/*
 * DTO de l'albarà (usat per l'endpoint de consulta via QR i per incloure'l a OrdreDTO).
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public class AlbaraDTO {

    public Integer id_albara;
    public String codi;
    public String dataGeneracio;
    public Double preuTotal;

    // Dades de l'ordre associada
    public Integer idOrdre;
    public String identificadorOrdre;
    public String estatOrdre;
    public String tendaDestinataria;
    public String adreca;
    public String ciutat;
    public String cp;
    public String telefon;
    public String temporada;
    public Integer quantitatPales;
    public Integer totalPaquets;
    public Double pesTotal;
    public String gestorResponsable;

    public AlbaraDTO(Albara a) {
        this.id_albara = a.getId_albara();
        this.codi = a.getCodi();
        this.dataGeneracio = a.getDataGeneracio() != null ? a.getDataGeneracio().toString() : null;
        
        // CORRECCIÓN: Convertir BigDecimal a Double para el DTO
        this.preuTotal = a.getPreuTotal() != null ? a.getPreuTotal().doubleValue() : 0.0;

        if (a.getOrdre() != null) {
            var o = a.getOrdre();
            this.idOrdre = o.getId_ordre();
            this.identificadorOrdre = o.getIdentificador();
            this.estatOrdre = o.getEstat();
            this.tendaDestinataria = o.getTendaDestinataria();
            this.adreca = o.getAdreca();
            this.ciutat = o.getCiutat();
            this.cp = o.getCp();
            this.telefon = o.getTelefon();
            this.temporada = o.getTemporada();
            this.quantitatPales = o.getQuantitatPales();
            this.totalPaquets = 0; 
            this.pesTotal = o.getPesTotal() != null ? o.getPesTotal().doubleValue() : 0.0;
            
            this.gestorResponsable = o.getGestor() != null ? o.getGestor().getNom() : "Sense gestor";
        }
    }
}