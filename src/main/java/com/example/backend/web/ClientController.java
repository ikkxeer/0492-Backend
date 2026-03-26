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
 *
 * @author samui
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/client")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    // Endpoint para obtener el total: GET /api/client/total
    @GetMapping("/total")
    public long countAll() {
        return clientService.getTotalClient();
    }

    
}
