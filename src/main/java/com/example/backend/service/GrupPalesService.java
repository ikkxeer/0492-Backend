/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.repo.GrupPalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per els gruppales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class GrupPalesService {
    
    @Autowired
    private GrupPalesRepository grupPalesRepository;
    
    // Devolver numero total de grup de pales
    public long getTotalGrupPales() {
        return grupPalesRepository.count();
    }
    
}