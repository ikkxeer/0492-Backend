/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.service;
import com.example.backend.domain.Albara;
import com.example.backend.domain.Ordre;
import com.example.backend.repo.AlbaraRepository;
import com.example.backend.web.dto.AlbaraDTO;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

/*
 * Service per generar i consultar albarans.
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class AlbaraService {

    @Autowired
    private AlbaraRepository albaraRepository;

    // Funció per generar la ordre
    public Albara generar(Ordre ordre) {
        String codi = "ALB-"
                + String.format("%03d", ordre.getId_ordre())
                + "-"
                + String.format("%06d", new Random().nextInt(900000) + 100000);

        Albara albara = new Albara();
        albara.setCodi(codi);
        albara.setOrdre(ordre);
        albara.setDataGeneracio(LocalDateTime.now());
        BigDecimal precio = (ordre.getPreu() != null) ? ordre.getPreu() : BigDecimal.ZERO;
        albara.setPreuTotal(precio);
        
        return albaraRepository.save(albara);
    }

    /**
     * Cerca un albarà pel seu codi (cridat per l'escàner QR).
     *
     * @param codi El codi del QR
     * @return AlbaraDTO amb totes les dades de l'ordre associada
     */
    public AlbaraDTO findByCodi(String codi) {
        return albaraRepository.findByCodi(codi)
                .map(AlbaraDTO::new)
                .orElseThrow(() -> new RuntimeException("Albarà no trobat: " + codi));
    }
}
