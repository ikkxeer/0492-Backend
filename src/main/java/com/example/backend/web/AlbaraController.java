/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;
import com.example.backend.domain.Albara;
import com.example.backend.service.AlbaraService;
import com.example.backend.web.dto.AlbaraDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albara")
@CrossOrigin(origins = "*")
public class AlbaraController {

    @Autowired
    private AlbaraService albaraService;

    /**
     * Genera un nuevo albarán para una orden.
     * POST /api/albara/generar/{idOrdre}
     */
    @PostMapping("/generar/{idOrdre}")
    public ResponseEntity<AlbaraDTO> generarAlbara(@PathVariable Integer idOrdre) {
        try {
            Albara nuevoAlbara = albaraService.generarParaOrden(idOrdre);
            return ResponseEntity.ok(new AlbaraDTO(nuevoAlbara));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint para el escáner QR.
     * GET /api/albara/{codi}
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