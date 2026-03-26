/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.repo.PaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per els pales
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class PaleService {

    @Autowired
    private PaleRepository paleRepository;

    // Devolver numero total de pales
    public long getTotalPales() {
        return paleRepository.count();
    }
}
