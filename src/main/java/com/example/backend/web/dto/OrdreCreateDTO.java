/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web.dto;

import java.util.List;

/**
 *
 * @author samui
 */
public class OrdreCreateDTO {
    public String adreca;
    public String ciutat;
    public String cp;
    public String telefon;
    public String prioritat;
    public String temporada;
    public Integer gestorId;
    public List<Integer> paleIds;
    public Double preu;
    public String tendaDestinataria;

    public OrdreCreateDTO() {}
}
