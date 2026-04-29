/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;
import com.example.backend.service.AlbaraService;
import com.example.backend.web.dto.AlbaraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Controlador per als albarans.
 * GET /api/albara/{codi} → Retorna les dades de l'ordre escanejant el QR.
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@RestController
@RequestMapping("/api/albara")
@CrossOrigin(origins = "*")
public class AlbaraController {

    @Autowired
    private AlbaraService albaraService;

    /**
     * Endpoint per a l'escàner QR.
     * GET /api/albara/{codi}
     *
     * Retorna totes les dades de l'ordre associada a l'albarà.
     */
    @GetMapping("/{codi}")
    public ResponseEntity<AlbaraDTO> getByCodi(@PathVariable String codi) {
        try {
            return ResponseEntity.ok(albaraService.findByCodi(codi));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
