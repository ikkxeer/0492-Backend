/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;
import com.example.backend.domain.Albara;
import com.example.backend.domain.Ordre;
import com.example.backend.repo.AlbaraRepository;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.web.dto.AlbaraDTO;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbaraService {

    @Autowired
    private AlbaraRepository albaraRepository;

    @Autowired
    private OrdreRepository ordreRepository;

    /**
     * Genera un albarán para una orden específica.
     */
    @Transactional
    public Albara generarParaOrden(Integer idOrdre) {
        // Buscar la orden
        Ordre ordre = ordreRepository.findById(idOrdre)
                .orElseThrow(() -> new RuntimeException("Ordre no trobada per ID: " + idOrdre));

        // Generar código único
        String codi = "ALB-" 
                + String.format("%03d", ordre.getId_ordre()) 
                + "-" 
                + String.format("%06d", new Random().nextInt(900000) + 100000);

        // Crear el albarán
        Albara albara = new Albara();
        albara.setCodi(codi);
        albara.setOrdre(ordre);
        albara.setDataGeneracio(LocalDateTime.now());
        
        BigDecimal precio = (ordre.getPreu() != null) ? ordre.getPreu() : BigDecimal.ZERO;
        albara.setPreuTotal(precio);

        // Guardar y retornar
        return albaraRepository.save(albara);
    }

    public AlbaraDTO findByCodi(String codi) {
        return albaraRepository.findByCodi(codi)
                .map(AlbaraDTO::new)
                .orElseThrow(() -> new RuntimeException("Albarà no trobat: " + codi));
    }
}