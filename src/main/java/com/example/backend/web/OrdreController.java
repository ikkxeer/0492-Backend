/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;
import com.example.backend.service.OrdreService;
import com.example.backend.web.dto.OrdreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador per ordres
 * 
 * /api/ordres: Retorna totes les ordres amb l'historial dins
 *  - /{id}: Retorna el detall d'una ordre
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@RestController
@RequestMapping("/api/ordres")
@CrossOrigin(origins = "*")
public class OrdreController {

    @Autowired
    private OrdreService ordreService;

    // Endpoint per obtenir totes les ordres: GET /api/ordres
    @GetMapping
    public ResponseEntity<List<OrdreDTO>> getOrdres(
        @RequestParam(required = false) String nom,
        @RequestParam(required = false) Integer rolId
    ) {
        List<OrdreDTO> ordres;

        if (rolId != null && rolId == 1) {
            ordres = ordreService.findAll();
        } else if (rolId != null && rolId == 3) {
            ordres = ordreService.findByMozoGrupo(nom);
        } else if (nom != null && !nom.isEmpty()) {
            ordres = ordreService.findByGestor(nom);
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
}