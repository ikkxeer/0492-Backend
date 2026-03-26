/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador pels clients
 * 
 * /api/client
 *  - /total: retorna el total de clients
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientController {
    // Atributs de la classe
    @Autowired
    private ClientService clientService;
    
    // Endpoint para obtener el total: GET /api/client/total
    @GetMapping("/total")
    public long countAll() {
        return clientService.getTotalClient();
    }

    
}
