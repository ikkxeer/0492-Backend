package com.example.backend.web;

import com.example.backend.service.AuthService;
import com.example.backend.web.dto.LoginRequest;
import com.example.backend.web.dto.LoginResponse;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador per l'autenticació
 * 
 * /api/auth
 *  - /login: realitzar el login i respondre amb el token
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Atributs de la classe
    private final AuthService authService;

    /**
     * Constructor default de la classe
     * 
     * @param authService Servei d'autenticació
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // /api/auth/login para hacer el login
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.username(), request.password());
        return new LoginResponse(token);
    }
}