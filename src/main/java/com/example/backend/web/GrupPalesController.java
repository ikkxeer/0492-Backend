/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;
import com.example.backend.domain.GrupPales;
import com.example.backend.repo.ClientRepository;
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
    
    @Autowired
    private ClientRepository clientRepository;


    // Helper per convertir entitat a DTO amb pales incloses
    private GrupPalesDTO convertToDTO(GrupPales g) {
        GrupPalesDTO dto = new GrupPalesDTO();
        dto.id = String.valueOf(g.getId_grup_pales());
        dto.referencia = g.getReferencia();
        dto.temporada = g.getTemporada();
        dto.proveidor = g.getProveidor() != null ? String.valueOf(g.getProveidor().getId_client()) : "";
        dto.dataEntrada = g.getDataEntrada() != null ? g.getDataEntrada().toLocalDate().toString() : "";
        dto.estat = g.getEstat();

        if (g.getPales() != null) {
            dto.pales = g.getPales().stream().map(p -> {
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
        } else {
            dto.pales = new java.util.ArrayList<>();
        }
        return dto;
    }

    // Endpoint per obtenir les tots els grups de pales: GET /api/gruppales
    @GetMapping
    public List<GrupPalesDTO> getAll() {
        return grupPalesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    // Endpoint per obtenir un grup de pales segons id: GET /api/gruppales/{id}
    @GetMapping("/{id}")
    public ResponseEntity<GrupPalesDTO> getById(@PathVariable Integer id) {
        return grupPalesService.findById(id)
                .map(g -> ResponseEntity.ok(convertToDTO(g)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint per crear un nou grup de pales: POST /api/gruppales
    @PostMapping
    public GrupPalesDTO create(@RequestBody GrupPalesDTO dto) {
        GrupPales grup = new GrupPales();
        grup.setReferencia(dto.referencia);
        grup.setTemporada(dto.temporada);
        grup.setEstat(dto.estat);
        if (dto.dataEntrada != null && !dto.dataEntrada.isEmpty()) {
            String dataStr = dto.dataEntrada.contains("T") ? dto.dataEntrada : dto.dataEntrada + "T00:00:00";
            grup.setDataEntrada(LocalDateTime.parse(dataStr));
        } else {
            grup.setDataEntrada(LocalDateTime.now());
        }
        if (dto.proveidor != null && !dto.proveidor.isEmpty()) {
            clientRepository.findById(Integer.valueOf(dto.proveidor))
                .ifPresent(grup::setProveidor);
        }

        // Gestionar les pales inicials si n'hi ha
        if (dto.pales != null && !dto.pales.isEmpty()) {
            java.util.List<com.example.backend.domain.Pale> llistaPales = new java.util.ArrayList<>();
            for (GrupPalesDTO.PaleDTO paleDTO : dto.pales) {
                com.example.backend.domain.Pale p = new com.example.backend.domain.Pale();
                p.setLot(paleDTO.lot);
                p.setSscc(paleDTO.sscc);
                p.setPaquets(paleDTO.paquets);
                p.setMesures(paleDTO.mesures);
                p.setEstat(paleDTO.estat != null ? paleDTO.estat : "disponible");
                p.setPes(paleDTO.pes != null ? java.math.BigDecimal.valueOf(paleDTO.pes) : null);
                if (paleDTO.dataExpedicio != null && !paleDTO.dataExpedicio.isEmpty()) {
                    p.setData_expedicio(LocalDateTime.parse(paleDTO.dataExpedicio + "T00:00:00"));
                }
                p.setGrupPales(grup);
                llistaPales.add(p);
            }
            grup.setPales(llistaPales);
        }

        GrupPales guardat = grupPalesService.save(grup);
        return convertToDTO(guardat);
    }

    // Endpoint per a actualitzar un grup de pales: PUT /api/gruppales/{id}
    @PutMapping("/{id}")
    public ResponseEntity<GrupPalesDTO> update(@PathVariable Integer id, @RequestBody GrupPalesDTO dto) {
        return grupPalesService.findById(id).map(grup -> {
            grup.setReferencia(dto.referencia);
            grup.setTemporada(dto.temporada);
            grup.setEstat(dto.estat);
            if (dto.dataEntrada != null && !dto.dataEntrada.isEmpty()) {
                String dataStr = dto.dataEntrada.contains("T") ? dto.dataEntrada : dto.dataEntrada + "T00:00:00";
                grup.setDataEntrada(LocalDateTime.parse(dataStr));
            }
            if (dto.proveidor != null && !dto.proveidor.isEmpty()) {
                clientRepository.findById(Integer.valueOf(dto.proveidor))
                    .ifPresent(grup::setProveidor);
            }

            // Actualitza les pales directament sobre la col·lecció del grup
            if (dto.pales != null) {
                for (GrupPalesDTO.PaleDTO paleDTO : dto.pales) {
                    com.example.backend.domain.Pale paleExistente = null;
                    
                    // Intentem trobar la pale si té un ID numèric vàlid
                    if (paleDTO.id != null && !paleDTO.id.startsWith("pale-")) {
                        paleExistente = grup.getPales().stream()
                            .filter(p -> String.valueOf(p.getId_pale()).equals(paleDTO.id))
                            .findFirst()
                            .orElse(null);
                    }

                    if (paleExistente != null) {
                        // ACTUALITZAR
                        paleExistente.setLot(paleDTO.lot);
                        paleExistente.setSscc(paleDTO.sscc);
                        paleExistente.setEstat(paleDTO.estat);
                        paleExistente.setPaquets(paleDTO.paquets);
                        paleExistente.setMesures(paleDTO.mesures);
                        paleExistente.setPes(paleDTO.pes != null ? java.math.BigDecimal.valueOf(paleDTO.pes) : null);
                        if (paleDTO.dataExpedicio != null && !paleDTO.dataExpedicio.isEmpty()) {
                            paleExistente.setData_expedicio(LocalDateTime.parse(paleDTO.dataExpedicio + "T00:00:00"));
                        }
                    } else {
                        // CREAR NOVA PALE i afegir-la al grup
                        com.example.backend.domain.Pale novaPale = new com.example.backend.domain.Pale();
                        novaPale.setLot(paleDTO.lot);
                        novaPale.setSscc(paleDTO.sscc);
                        novaPale.setPaquets(paleDTO.paquets);
                        novaPale.setMesures(paleDTO.mesures);
                        novaPale.setEstat(paleDTO.estat != null ? paleDTO.estat : "disponible");
                        novaPale.setPes(paleDTO.pes != null ? java.math.BigDecimal.valueOf(paleDTO.pes) : null);
                        if (paleDTO.dataExpedicio != null && !paleDTO.dataExpedicio.isEmpty()) {
                            novaPale.setData_expedicio(LocalDateTime.parse(paleDTO.dataExpedicio + "T00:00:00"));
                        }
                        novaPale.setGrupPales(grup);
                        grup.getPales().add(novaPale);
                    }
                }
            }

            GrupPales guardat = grupPalesService.save(grup);
            return ResponseEntity.ok(convertToDTO(guardat));
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