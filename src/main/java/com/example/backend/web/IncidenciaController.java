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
 *
 * @author samui
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/incidencies")
public class IncidenciaController {

    @Autowired
    private IncidenciaService service;

    @GetMapping
    public List<Incidencia> listar(
            @RequestParam String rol, 
            @RequestParam Integer userId) {
        return service.obtenerSegunRol(rol, userId);
    }

    // He dejado solo uno de los @PostMapping
    @PostMapping
    public Incidencia crear(@RequestBody Incidencia inc) {
        return service.crear(inc);
    }
    
    @PatchMapping("/{id}/estat")
    public ResponseEntity<Incidencia> modificarEstat(
            @PathVariable Integer id,
            @RequestParam String nouEstat,
            @RequestParam String autor) {
        
        Incidencia actualizada = service.actualizarEstado(id, nouEstat, autor);
        return ResponseEntity.ok(actualizada);
    }
}