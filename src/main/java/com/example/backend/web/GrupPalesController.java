/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;
import com.example.backend.domain.GrupPales;
import com.example.backend.repo.GrupPalesRepository;
import com.example.backend.repo.PaleRepository;
import com.example.backend.service.GrupPalesService;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * Controlador per gruppales
 * 
 * /api/gruppales: retorna tots els grup de pales
 *  - /total: retorna el total de gruppales
 *  - /{id}: retorna un grup de pale segons l'id
 *  - POST: crea un grup de pale passat per parametre
 *  - PUT /{id}: actualitza un grup de pale passat per parametre
 *  - DELETE /{id}: elimina un grup de pale segons l'id
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/gruppales")
public class GrupPalesController {

    // Atributs de la classe
    @Autowired
    private GrupPalesService grupPalesService;
    
    @Autowired
    private GrupPalesRepository grupPalesRepository;

    @Autowired
    private PaleRepository paleRepository;


    // Endpoint per obtenir les tots els grups de pales: GET /api/gruppales
    @GetMapping
    public List<GrupPalesDTO> getAll() {
        return grupPalesRepository.findAll().stream().map(g -> {
            GrupPalesDTO dto = new GrupPalesDTO();
            dto.id = String.valueOf(g.getId_grup_pales());
            dto.referencia = g.getReferencia();
            dto.temporada = g.getTemporada();
            dto.proveidor = g.getProveidor() != null ? g.getProveidor().getNom() : "";
            dto.dataEntrada = g.getDataEntrada() != null 
                ? g.getDataEntrada().toLocalDate().toString() : "";
            dto.estat = g.getEstat();

            // Obtener las pales de este grupo
            dto.pales = paleRepository.findByIdGrupPales(g.getId_grup_pales())
                .stream().map(p -> {
                    GrupPalesDTO.PaleDTO pale = new GrupPalesDTO.PaleDTO();
                    pale.id = String.valueOf(p.getId_pale());
                    pale.lot = p.getLot();
                    pale.sscc = p.getSscc();
                    pale.pes = p.getPes() != null ? p.getPes().doubleValue() : null;
                    pale.mesures = p.getMesures();
                    pale.paquets = p.getPaquets();
                    pale.temporada = g.getTemporada();
                    pale.dataExpedicio = p.getData_expedicio() != null
                        ? p.getData_expedicio().toLocalDate().toString() : "";
                    pale.clientProveidor = g.getProveidor() != null 
                        ? g.getProveidor().getNom() : "";
                    pale.estat = p.getEstat();
                    return pale;
                }).collect(java.util.stream.Collectors.toList());

            return dto;
        }).collect(java.util.stream.Collectors.toList());
    }

    // Endpoint per obtenir un grup de pales segons id: GET /api/gruppales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<GrupPales> getById(@PathVariable Integer id) {
        return grupPalesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint per crear un nou grup de pales: POST /api/gruppales
    @PostMapping
    public GrupPales create(@RequestBody GrupPales grup) {
        if (grup.getDataEntrada() == null) {
            grup.setDataEntrada(LocalDateTime.now());
        }
        return grupPalesService.save(grup);
    }

    // Endpoint per a actualitzar un grup de pales: PUT /api/gruppales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<GrupPales> update(@PathVariable Integer id, @RequestBody GrupPales details) {
        return grupPalesService.findById(id).map(grup -> {
            grup.setReferencia(details.getReferencia());
            grup.setTemporada(details.getTemporada());
            grup.setEstat(details.getEstat());
            grup.setProveidor(details.getProveidor());
            return ResponseEntity.ok(grupPalesService.save(grup));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Endpoint per a eliminar un grup de pales segons id: DELETE /api/gruppales/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (grupPalesService.findById(id).isPresent()) {
            grupPalesService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint per a obtenir el total de grup de pales: GET /api/gruppales/total
    @GetMapping("/total")
    public long countAll() {
        return grupPalesService.findAll().size();
    }
}