package com.example.backend.web;

import com.example.backend.service.AuthService;
import com.example.backend.web.dto.LoginRequest;
import com.example.backend.web.dto.LoginResponse;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Problemas con CORS solucionados para aceptar todos los origenes
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.username(), request.password());
        return new LoginResponse(token);
    }
}