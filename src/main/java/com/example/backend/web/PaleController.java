/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.domain.GrupPales;
import com.example.backend.domain.Pale;
import com.example.backend.repo.GrupPalesRepository;
import com.example.backend.repo.PaleRepository;
import com.example.backend.service.PaleService;
import com.example.backend.web.dto.GrupPalesDTO;
import java.time.LocalDateTime;
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
 * * /api/pales (GET): Retorna tots els pales
 * - /api/pales/{id} (GET): Retorna un pale especific segons ID
 * - /total (GET): retorna el total de pales
 * - POST: Crea un pale passat per el body
 * - PUT: Actualitza un pale
 * - DELETE: Elimina un pale
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
    
    @Autowired
    private PaleRepository paleRepository;
    
    @Autowired
    private GrupPalesRepository grupPalesRepository;


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

    // Endpoint per obtenir un pale en especific segons el id: GET /api/pales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pale> getById(@PathVariable Integer id) {
        return paleService.findPaleById(id)
                .map(pale -> ResponseEntity.ok(pale))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per obtenir tots els pales segons estat passat per parametre: GET /api/pales/total/estado
    @GetMapping("/total/estado")
    public long countByEstado(@RequestParam String nombre) {
        // Usamos el objeto inyectado 'paleService' (en minúscula)
        return paleService.countByEstado(nombre); 
    }
    
    // Endpoint per crear un pale: POST /api/pales
    @PostMapping
    public ResponseEntity<Pale> create(@RequestParam Integer grupId, @RequestBody GrupPalesDTO.PaleDTO dto) {
        return grupPalesRepository.findById(grupId).map(grup -> {
            Pale pale = new Pale();
            pale.setLot(dto.lot);
            pale.setSscc(dto.sscc);
            pale.setEstat(dto.estat);
            pale.setPaquets(dto.paquets);
            pale.setMesures(dto.mesures);

            if (dto.pes != null) pale.setPes(java.math.BigDecimal.valueOf(dto.pes));
            if (dto.dataExpedicio != null && !dto.dataExpedicio.isEmpty()) {
                String dataStr = dto.dataExpedicio.contains("T") ? dto.dataExpedicio : dto.dataExpedicio + "T00:00:00";
                pale.setData_expedicio(LocalDateTime.parse(dataStr));
            }
            pale.setGrupPales(grup); 

            return ResponseEntity.ok(paleRepository.save(pale));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per editar un pale: PUT /api/pales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pale> update(@PathVariable Integer id, @RequestBody GrupPalesDTO.PaleDTO dto) {
        return paleRepository.findById(id).map(pale -> {
            pale.setLot(dto.lot);
            pale.setSscc(dto.sscc);
            pale.setEstat(dto.estat);
            pale.setPaquets(dto.paquets);
            pale.setMesures(dto.mesures);
            if (dto.pes != null) pale.setPes(java.math.BigDecimal.valueOf(dto.pes));
            if (dto.dataExpedicio != null && !dto.dataExpedicio.isEmpty()) {
                pale.setData_expedicio(LocalDateTime.parse(dto.dataExpedicio + "T00:00:00"));
            }
            return ResponseEntity.ok(paleRepository.save(pale));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // Endpoint per eliminar un pale: DELETE /api/pales/{id} 
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return paleRepository.findById(id).map(pale -> {
            GrupPales grup = pale.getGrupPales();
            if (grup != null) {
                // Elimina la pale de la llista del grup i deixa que CascadeType.ALL faci la feina
                grup.getPales().removeIf(p -> p.getId_pale().equals(id));
                grupPalesRepository.save(grup);
            } else {
                // Pale sense grup, ho elimina directament
                paleRepository.deleteById(id);
            }
            return ResponseEntity.<Void>ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
    
}