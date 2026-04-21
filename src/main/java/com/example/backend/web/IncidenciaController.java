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
import org.springframework.http.ResponseEntity;

/**
 * Controlador per incidencies
 * * /api/incidencies: retorna les incidencies segons el rol
 * - POST: crea una incidencia passada per parametre
 * - PATCH /{id}/estat: actualitza l'estat d'una incidencia
 *
 * @author samui
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/incidencies")
public class IncidenciaController {

    // Atributs de la classe
    @Autowired
    private IncidenciaService service;

    // Endpoint per obtenir unes incidencies segons rol: GET /api/incidencies
    @GetMapping
    public List<Incidencia> listar(
            @RequestParam String rol, 
            @RequestParam Integer userId) {
        return service.obtenerSegunRol(rol, userId);
    }

    // Endpoint per crear una incidencia: POST /api/incidencies
    @PostMapping
    public Incidencia crear(@RequestBody Incidencia inc) {
        return service.crear(inc);
    }
    
    // Endpoint per a modificar l'estat d'una incidencia: PATCH /api/incidencies/{id}/estat
    @PatchMapping("/{id}/estat")
    public ResponseEntity<Incidencia> modificarEstat(
            @PathVariable Integer id,
            @RequestParam String nouEstat,
            @RequestParam String autor) {
        
        Incidencia actualizada = service.actualizarEstado(id, nouEstat, autor);
        return ResponseEntity.ok(actualizada);
    }
}