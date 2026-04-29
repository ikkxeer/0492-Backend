/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.OrdreService;
import com.example.backend.web.dto.OrdreCreateDTO;
import com.example.backend.web.dto.OrdreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * Controlador per ordres
 *
 * /api/ordres: Retorna les ordres filtrades per rol:
 *   rolId=1 (ADMIN): totes les ordres
 *   rolId=2 (GESTOR): ordres on ell és el gestor (?nom=)
 *   rolId=3 (MOZO): ordres del seu grup de mozos (?nom=)
 *   rolId=4 (TRANSPORTISTA): ordres assignades a ell (?nom=)
 *
 * - POST: Crea una nova ordre
 * - PUT: Actualitza una ordre
 * - DELETE: Elimina una ordre
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@RestController
@RequestMapping("/api/ordres")
@CrossOrigin(origins = "*")
public class OrdreController {

    @Autowired
    private OrdreService ordreService;

    // Endpoint per obtenir les ordres filtrades per rol: GET /api/ordres
    @GetMapping
    public ResponseEntity<List<OrdreDTO>> getOrdres(
        @RequestParam(required = false) String nom,
        @RequestParam(required = false) Integer rolId
    ) {
        List<OrdreDTO> ordres;

        if (rolId != null && rolId == 1) {
            // ADMIN: veu totes les ordres
            ordres = ordreService.findAll();

        } else if (rolId != null && rolId == 2) {
            // GESTOR: veu les ordres on ell és el gestor responsable
            ordres = (nom != null && !nom.isEmpty())
                ? ordreService.findByGestor(nom)
                : List.of();

        } else if (rolId != null && rolId == 3) {
            // MOZO: veu les ordres assignades al seu grup de mozos
            ordres = (nom != null && !nom.isEmpty())
                ? ordreService.findByMozoGrupo(nom)
                : List.of();

        } else if (rolId != null && rolId == 4) {
            // TRANSPORTISTA: veu les ordres assignades directament a ell
            ordres = (nom != null && !nom.isEmpty())
                ? ordreService.findByTransportista(nom)
                : List.of();

        } else {
            ordres = List.of();
        }

        return ResponseEntity.ok(ordres);
    }

    // Endpoint per obtenir el detall d'una ordre: GET /api/ordres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrdreDTO> getDetall(@PathVariable String id) {
        return ordreService.findByIdentificador(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per crear una nova ordre: POST /api/ordres
    @PostMapping
    public ResponseEntity<OrdreDTO> crear(@RequestBody OrdreCreateDTO dto) {
        OrdreDTO nueva = ordreService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }
    
    // Endpoint per actualizar una ordre: PUT /api/ordres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<OrdreDTO> actualizar(@PathVariable Integer id, @RequestBody OrdreCreateDTO dto) {
        try {
            OrdreDTO actualizada = ordreService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Endpoint per eliminar una ordre: DELETE /api/ordres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            ordreService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
