/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.Incidencia;
import com.example.backend.service.IncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *
 * @author samui
 */
@RestController
@RequestMapping("/api/incidencies")
@CrossOrigin(origins = "*")
public class IncidenciaController {

    @Autowired
    private IncidenciaService service;

    // Coincide con obtenirIncidencies(rol, userId) del frontend
    @GetMapping
    public List<Incidencia> listar(
            @RequestParam String rol, 
            @RequestParam Integer userId) {
        return service.obtenerSegunRol(rol, userId);
    }

    @PostMapping
    public Incidencia guardar(@RequestBody Incidencia inc) {
        return service.crear(inc);
    }
}