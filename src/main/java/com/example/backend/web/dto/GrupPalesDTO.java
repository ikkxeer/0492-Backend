/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;
import java.util.List;

/**
 * DTO que representa un GrupPale
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public class GrupPalesDTO {
    public String id;
    public String referencia;
    public String temporada;
    public String proveidor;
    public String dataEntrada;
    public String estat;
    public List<PaleDTO> pales;

    public static class PaleDTO {
        public String id;
        public String lot;
        public String sscc;
        public Double pes;
        public String mesures;
        public Integer paquets;
        public String temporada;
        public String dataExpedicio;
        public String clientProveidor;
        public String estat;
    }
}
