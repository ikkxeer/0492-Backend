package com.example.backend.web;

import com.example.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/pales-setmana")
    public ResponseEntity<?> getPalesSetmana() {
        return ResponseEntity.ok(List.of(
            Map.of("dia", "Dl", "total", 280),
            Map.of("dia", "Dm", "total", 310),
            Map.of("dia", "Dc", "total", 290),
            Map.of("dia", "Dj", "total", 430),
            Map.of("dia", "Dv", "total", 400),
            Map.of("dia", "Ds", "total", 170),
            Map.of("dia", "Dg", "total", 150)
        ));
    }

    @GetMapping("/tendencia-entregues")
    public ResponseEntity<?> getTendenciaEntregues() {
        return ResponseEntity.ok(List.of(
            Map.of("mes", "Gen", "total", 2300),
            Map.of("mes", "Feb", "total", 2500),
            Map.of("mes", "Mar", "total", 2480),
            Map.of("mes", "Abr", "total", 2600),
            Map.of("mes", "Mai", "total", 2580),
            Map.of("mes", "Jun", "total", 3800)
        ));
    }

    @GetMapping("/activitat-recent")
    public ResponseEntity<?> getActivitatRecent() {
        return ResponseEntity.ok(List.of(
            Map.of("id", 1, "descripcio", "Pala #PAL-1234 entregada", "temps", "Fa 5 minuts", "tipus", "entrega"),
            Map.of("id", 2, "descripcio", "Nova ordre creada #ORD-5678", "temps", "Fa 12 minuts", "tipus", "ordre"),
            Map.of("id", 3, "descripcio", "Incidència reportada #INC-9012", "temps", "Fa 23 minuts", "tipus", "incidencia")
        ));
    }

    // --- RUTES GESTOR ---

    @GetMapping("/gestor/stats")
    public ResponseEntity<?> getGestorStats() {
        // En un entorn segur llegiríem el userId del JWT
        return ResponseEntity.ok(dashboardService.getDashboardStats());
    }

    @GetMapping("/gestor/pales-setmana")
    public ResponseEntity<?> getGestorPalesSetmana() {
        return getPalesSetmana();
    }

    @GetMapping("/gestor/tendencia-entregues")
    public ResponseEntity<?> getGestorTendenciaEntregues() {
        return getTendenciaEntregues();
    }

    @GetMapping("/gestor/activitat-recent")
    public ResponseEntity<?> getGestorActivitatRecent() {
        return getActivitatRecent();
    }
}
