/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.Pale;
import com.example.backend.service.PaleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador pels pales
 * 
 * /api/pales (GET): Retorna tots els pales
 *  - /total (GET): retorna el total de pales
 *  - POST: Crea un pale passat per el body
 *  - PUT: Actualitza un pale
 *  - DELETE: Elimina un pale
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pales")
public class PaleController {
    // Atrbiuts de la classe
    @Autowired
    private PaleService paleService;

    // Endpoint para obtener el total: GET /api/pales/total
    @GetMapping("/total")
    public long countAll() {
        return paleService.getTotalPales();
    }
    
    // Endpoint per obtenir tots els pales: GET /api/pales
    @GetMapping
    public List<Pale> getAll() {
        return paleService.findAllPales();
    }
    
    // Endpoint per obtenir tots els pales segons estat passat per parametre: GET /api/pales/total/estado
    @GetMapping("/total/estado")
    public long countByEstado(@RequestParam String nombre) {
        // Usamos el objeto inyectado 'paleService' (en minúscula)
        return paleService.countByEstado(nombre); 
    }
    
    // Endpoint per crear un pale: POST /api/pales
    @PostMapping
    public Pale create(@RequestBody Pale pale) {
        return paleService.savePale(pale);
    }
    
    // Endpoint per editar un pale: PUT /api/pales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pale> update(@PathVariable Integer id, @RequestBody Pale paleDetails) {
        return paleService.findPaleById(id).map(pale -> {
            
            // Actualitzem els camps del pale que hem rebut
            pale.setLot(paleDetails.getLot());
            pale.setSscc(paleDetails.getSscc());
            pale.setPes(paleDetails.getPes());
            pale.setMesures(paleDetails.getMesures());
            pale.setPaquets(paleDetails.getPaquets());
            pale.setEstat(paleDetails.getEstat());
            
            // Actualitzem el pale
            Pale updated = paleService.savePale(pale);
            
            // Retornem al resposta
            return ResponseEntity.ok(updated);
            
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per eliminar un pale: DELETE /api/pales/{id} 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // Si el pale existeix
        if (paleService.findPaleById(id).isPresent()) {
            // Eliminem el pale
            paleService.deletePale(id);
            
            // Retornem la resposta
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}