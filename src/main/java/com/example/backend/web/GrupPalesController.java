/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.GrupPalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador per grupmozos
 * 
 * /api/grupmozos
 *  - /total: retorna el total de grupmozos
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/gruppales")
public class GrupPalesController {
    // Atributs de la classe
    @Autowired
    private GrupPalesService grupPalesService;

    // Endpoint para obtener el total: GET /api/gruppales/total
    @GetMapping("/total")
    public long countAll() {
        return grupPalesService.getTotalGrupPales();
    }

}