/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.repo.ClientRepository;
import com.example.backend.repo.GrupMozosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per els clients
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    // Devolver numero total de grup de mozos
    public long getTotalClient() {
        return clientRepository.count();
    }
    
}
