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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        
        // Entregades (ara sobre ORDRES, no sobre pales individuals)
        dto.setEntregades(ordreRepo.countByEstat("ENTREGAT"));
        dto.setEntregadesPercent(0); 
        
        return dto;
    }

    public DashboardStatsDTO getGestorStats(Integer userId) {
        // En un entorn real faríem queries on clàusula WHERE reportatPer = userId
        // Per ara aprofitem la mateixa estructura però modificant valors
        DashboardStatsDTO dto = getDashboardStats();
        // Fem un mock sobre les incidències per diferenciar (opcional)
        return dto;
    }

    public List<Map<String, Object>> getOrdresSetmana() {
        LocalDate today = LocalDate.now();
        // Buscar el dilluns de la setmana actual
        LocalDate startOfWeek = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        
        List<Map<String, Object>> result = new ArrayList<>();
        String[] dies = {"Dl", "Dm", "Dc", "Dj", "Dv", "Ds", "Dg"};
        
        for (int i = 0; i < 7; i++) {
            LocalDate currentDay = startOfWeek.plusDays(i);
            LocalDateTime startOfDay = currentDay.atStartOfDay();
            LocalDateTime endOfDay = currentDay.atTime(LocalTime.MAX);
            
            long total = ordreRepo.countByDataCreacioBetween(startOfDay, endOfDay);
            result.add(Map.of("dia", dies[i], "total", total));
        }
        return result;
    }

    public List<Map<String, Object>> getTendenciaEntregues() {
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] mesos = {"Gen", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Des"};
        
        // Mostrar els últims 6 mesos, incloent l'actual
        for (int i = 5; i >= 0; i--) {
            LocalDate targetMonth = today.minusMonths(i);
            LocalDateTime startOfMonth = targetMonth.withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth()).atTime(LocalTime.MAX);
            // Comptem ORDRES entregades en aquest mes
            long total = ordreRepo.countByEstatAndDataCreacioBetween("ENTREGAT", startOfMonth, endOfMonth);
            result.add(Map.of("mes", mesos[targetMonth.getMonthValue() - 1], "total", total));
        }
        return result;
    }

    public List<Map<String, Object>> getIncidenciesPerEstat() {
        List<Object[]> data = incidenciaRepo.countByEstat();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : data) {
            String estat = row[0] != null ? row[0].toString() : "Sense estat";
            // Mapegem noms interns a noms legibles (opcional, ho podem fer al front)
            result.add(Map.of(
                "name", estat,
                "value", row[1]
            ));
        }
        return result;
    }
}
