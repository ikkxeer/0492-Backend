/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.GrupMozosService;
import com.example.backend.service.PaleService;
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
@RequestMapping("/api/grupmozos")
public class GrupMozosController {
    // Atributs de la classe
    @Autowired
    private GrupMozosService grupMozosService;

    // Endpoint para obtener el total: GET /api/grupmozos/total
    @GetMapping("/total")
    public long countAll() {
        return grupMozosService.getTotalGrupMozos();
    }

    
}
