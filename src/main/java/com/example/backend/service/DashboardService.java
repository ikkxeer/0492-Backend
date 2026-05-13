package com.example.backend.service;

import com.example.backend.repo.PaleRepository;
import com.example.backend.repo.OrdreRepository;
import com.example.backend.repo.IncidenciaRepository;
import com.example.backend.web.dto.DashboardStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class DashboardService {

    @Autowired
    private PaleRepository paleRepo;

    @Autowired
    private OrdreRepository ordreRepo;

    @Autowired
    private IncidenciaRepository incidenciaRepo;

    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();
        
        // Pales actives són les que estan en estat DISPONIBLE
        long palesActives = paleRepo.countByEstat("DISPONIBLE");
        dto.setPalesActives(palesActives);
        dto.setPalesActivesPercent(0); 
        
        // Ordres avui (reals des de la BBDD)
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long ordresAvui = ordreRepo.countByDataCreacioBetween(startOfDay, endOfDay);
        dto.setOrdresAvui(ordresAvui);
        dto.setOrdresAvuiPercent(0);
        
        // Incidències
        dto.setIncidencies(incidenciaRepo.count());
        dto.setIncidenciesPercent(0);
        
        // Entregades
        dto.setEntregades(paleRepo.countByEstat("ENTREGAT"));
        dto.setEntregadesPercent(2);
        
        return dto;
    }

    public DashboardStatsDTO getGestorStats(Integer userId) {
        // En un entorn real faríem queries on clàusula WHERE reportatPer = userId
        // Per ara aprofitem la mateixa estructura però modificant valors
        DashboardStatsDTO dto = getDashboardStats();
        // Fem un mock sobre les incidències per diferenciar (opcional)
        return dto;
    }
}
