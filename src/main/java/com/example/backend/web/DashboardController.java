/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.backend.web;

import com.example.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador pel Dashboard
 * 
 * /api/dashboard
 *  - /stats: estadístiques generals
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    // Atributs de la classe
    @Autowired
    private DashboardService dashboardService;

    // Endpoint per obtenir les estadístiques: GET /api/dashboard/stats
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    // Endpoint per obtenir les ordres de la setmana: GET /api/dashboard/pales-setmana
    @GetMapping("/pales-setmana")
    public ResponseEntity<?> getPalesSetmana() {
        return ResponseEntity.ok(dashboardService.getOrdresSetmana());
    }

    // Endpoint per obtenir la tendència d'entregues: GET /api/dashboard/tendencia-entregues
    @GetMapping("/tendencia-entregues")
    public ResponseEntity<?> getTendenciaEntregues() {
        return ResponseEntity.ok(dashboardService.getTendenciaEntregues());
    }

    // Endpoint per obtenir l'activitat recent: GET /api/dashboard/activitat-recent
    @GetMapping("/activitat-recent")
    public ResponseEntity<?> getActivitatRecent() {
        return ResponseEntity.ok(List.of(
                Map.of("id", 1, "descripcio", "Pala #PAL-1234 entregada", "temps", "Fa 5 minuts", "tipus", "entrega"),
                Map.of("id", 2, "descripcio", "Nova ordre creada #ORD-5678", "temps", "Fa 12 minuts", "tipus", "ordre"),
                Map.of("id", 3, "descripcio", "Incidència reportada #INC-9012", "temps", "Fa 23 minuts", "tipus",
                        "incidencia")));
    }

    // Endpoint per obtenir les incidències per estat: GET /api/dashboard/incidencies-estat
    @GetMapping("/incidencies-estat")
    public ResponseEntity<?> getIncidenciesEstat() {
        return ResponseEntity.ok(dashboardService.getIncidenciesPerEstat());
    }

    // Endpoint per obtenir les estadístiques del gestor: GET /api/dashboard/gestor/stats
    @GetMapping("/gestor/stats")
    public ResponseEntity<?> getGestorStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    // Endpoint per obtenir les ordres de la setmana pel gestor: GET /api/dashboard/gestor/pales-setmana
    @GetMapping("/gestor/pales-setmana")
    public ResponseEntity<?> getGestorPalesSetmana() {
        return getPalesSetmana();
    }

    // Endpoint per obtenir la tendència d'entregues pel gestor: GET /api/dashboard/gestor/tendencia-entregues
    @GetMapping("/gestor/tendencia-entregues")
    public ResponseEntity<?> getGestorTendenciaEntregues() {
        return getTendenciaEntregues();
    }

    // Endpoint per obtenir l'activitat recent pel gestor: GET /api/dashboard/gestor/activitat-recent
    @GetMapping("/gestor/activitat-recent")
    public ResponseEntity<?> getGestorActivitatRecent() {
        return getActivitatRecent();
    }

    // Endpoint per obtenir les incidències per estat pel gestor: GET /api/dashboard/gestor/incidencies-estat
    @GetMapping("/gestor/incidencies-estat")
    public ResponseEntity<?> getGestorIncidenciesEstat() {
        return getIncidenciesEstat();
    }

    // Endpoint per obtenir el resum de clients totals de la base de dades: GET /api/dashboard/clients-totals
    @GetMapping("/clients-totals")
    public ResponseEntity<?> getClientsTotals() {
        return ResponseEntity.ok(dashboardService.getClientsTotals());
    }
}
