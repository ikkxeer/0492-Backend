/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 * Service per el dashboard
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class DashboardService {

    @Autowired
    private PaleRepository paleRepo;

    @Autowired
    private OrdreRepository ordreRepo;

    @Autowired
    private IncidenciaRepository incidenciaRepo;

    // Retorna les estadístiques generals del dashboard
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO dto = new DashboardStatsDTO();

        long palesActives = paleRepo.countByEstat("DISPONIBLE");
        dto.setPalesActives(palesActives);
        dto.setPalesActivesPercent(0);

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        long ordresAvui = ordreRepo.countByDataCreacioBetween(startOfDay, endOfDay);
        dto.setOrdresAvui(ordresAvui);
        dto.setOrdresAvuiPercent(0);

        dto.setIncidencies(incidenciaRepo.count());
        dto.setIncidenciesPercent(0);

        dto.setEntregades(ordreRepo.countByEstat("ENTREGAT"));
        dto.setEntregadesPercent(0);

        return dto;
    }

    // Retorna les estadístiques del dashboard per un gestor concret
    public DashboardStatsDTO getGestorStats(Integer userId) {
        DashboardStatsDTO dto = getDashboardStats();
        return dto;
    }

    // Retorna el total d'ordres per cada dia de la setmana actual
    public List<Map<String, Object>> getOrdresSetmana() {
        LocalDate today = LocalDate.now();
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

    // Retorna la tendència d'entregues dels últims 6 mesos
    public List<Map<String, Object>> getTendenciaEntregues() {
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] mesos = {"Gen", "Feb", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Des"};

        for (int i = 5; i >= 0; i--) {
            LocalDate targetMonth = today.minusMonths(i);
            LocalDateTime startOfMonth = targetMonth.withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth()).atTime(LocalTime.MAX);
            long total = ordreRepo.countByEstatAndDataCreacioBetween("ENTREGAT", startOfMonth, endOfMonth);
            result.add(Map.of("mes", mesos[targetMonth.getMonthValue() - 1], "total", total));
        }
        return result;
    }

    // Retorna el nombre d'incidències agrupades per estat
    public List<Map<String, Object>> getIncidenciesPerEstat() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(Map.of("name", "obert", "value", incidenciaRepo.countByEstat("obert")));
        result.add(Map.of("name", "en_proces", "value", incidenciaRepo.countByEstat("en_proces")));
        result.add(Map.of("name", "resolt", "value", incidenciaRepo.countByEstat("resolt")));
        result.add(Map.of("name", "tancat", "value", incidenciaRepo.countByEstat("tancat")));
        return result;
    }

    // Retorna els 5 grups de pales amb més unitats
    public List<Map<String, Object>> getPalesPerGrup() {
        List<Map<String, Object>> result = new ArrayList<>();
        paleRepo.findAll().stream()
                .filter(p -> p.getGrupPales() != null)
                .collect(java.util.stream.Collectors.groupingBy(p -> p.getGrupPales().getReferencia(),
                        java.util.stream.Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> result.add(Map.of("name", entry.getKey(), "value", entry.getValue())));
        return result;
    }

    // Retorna el nombre d'ordres agrupades per estat
    public List<Map<String, Object>> getOrdresPerEstat() {
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(Map.of("name", "Pendent", "value", ordreRepo.countByEstat("PENDENT")));
        result.add(Map.of("name", "Confirmat", "value", ordreRepo.countByEstat("CONFIRMAT")));
        result.add(Map.of("name", "En Trànsit", "value", ordreRepo.countByEstat("EN TRÀNSIT")));
        result.add(Map.of("name", "Entregat", "value", ordreRepo.countByEstat("ENTREGAT")));
        return result;
    }
}
