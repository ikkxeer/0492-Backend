/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    // Constructor de la classe
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint per fer el login: POST /api/auth/login
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        return new LoginResponse(token);
    }
}