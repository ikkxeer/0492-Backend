/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;
import com.example.backend.domain.*;
import com.example.backend.repo.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author samui
 */
@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repo;

    public List<Incidencia> obtenerSegunRol(String rol, Integer userId) {
        if ("ADMIN".equals(rol)) return repo.findAll();
        
        if ("GESTOR".equals(rol)) {
            return repo.findAll().stream()
                .filter(i -> userId.equals(i.getAssignatA()) || userId.equals(i.getReportatPer()))
                .toList();
        }
        
        if ("MOZO".equals(rol)) return repo.findByAssignatA(userId);
        if ("TRANSPORTISTA".equals(rol)) return repo.findByReportatPer(userId);
        
        return List.of();
    }

    public Incidencia crear(Incidencia inc) {
        inc.setDataCreacio(LocalDateTime.now()); 
        inc.setEstat("obert");
        
        if (inc.getReportatPer() != null) {
            inc.getHistorial().add(new EntradaHistorial(
                "Incidència creada", 
                "Creada por usuario ID: " + inc.getReportatPer(), 
                inc.getReportatPer().toString()
            ));
        }
        
        return repo.save(inc);
    }
}