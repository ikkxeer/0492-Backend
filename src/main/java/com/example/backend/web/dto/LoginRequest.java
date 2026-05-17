package com.example.backend.web.dto;

/**
 * DTO per la petició de login
 * 
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
public record LoginRequest(String email, String password) {
}
