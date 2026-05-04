/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;

import com.example.backend.domain.GrupMozos;
import com.example.backend.repo.GrupMozosRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per els grupmozos
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class GrupMozosService {
    
    @Autowired
    private GrupMozosRepository grupMozosRepository;
    
    // Devolver numero total de grup de mozos
    public long getTotalGrupMozos() {
        return grupMozosRepository.count();
    }
    
    // Buscar grupo por ID de usuario mozo
    public Optional<GrupMozos> getGrupoByUsuariId(Integer idUsuari) {
        return grupMozosRepository.findByUsuariId(idUsuari);
    }
    
    // Devuelve todos los grupos de mozo
    public List<GrupMozos> getAllGrupMozos() {
        return grupMozosRepository.findAll();
    }
}