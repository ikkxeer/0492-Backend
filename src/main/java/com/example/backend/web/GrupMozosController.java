/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.GrupMozos;
import com.example.backend.service.GrupMozosService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador per grupmozos
 * 
 * /api/grupmozos
 *  - /total: retorna el total de grupmozos
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/grupmozos")
public class GrupMozosController {
    // Atributs de la classe
    @Autowired
    private GrupMozosService grupMozosService;

    // Endpoint per obtenir el total: GET /api/grupmozos/total
    @GetMapping("/total")
    public long countAll() {
        return grupMozosService.getTotalGrupMozos();
    }
    
    // Endpoint per obtenir el grup d'un usuari mozo: GET /api/grupmozos/usuario/{idUsuari}
    @GetMapping("/usuario/{idUsuari}")
    public ResponseEntity<GrupMozos> getGrupoPorUsuario(@PathVariable Integer idUsuari) {
        return grupMozosService.getGrupoByUsuariId(idUsuari)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per obtenir tots els grups de mozos: GET /api/grupmozos
    @GetMapping
    public List<GrupMozos> getAll() {
        return grupMozosService.getAllGrupMozos();
    }

}
