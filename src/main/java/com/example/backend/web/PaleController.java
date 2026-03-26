/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.PaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador pels pales
 * 
 * /api/pales
 *  - /total: retorna el total de pales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pales")
public class PaleController {
    // Atrbiuts de la classe
    @Autowired
    private PaleService paleService;

    // Endpoint para obtener el total: GET /api/pales/total
    @GetMapping("/total")
    public long countAll() {
        return paleService.getTotalPales();
    }
}