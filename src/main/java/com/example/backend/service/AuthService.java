package com.example.backend.service;

import com.example.backend.domain.UserAccount;
import com.example.backend.repo.UserRepository;
import com.example.backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service per l'autenticació
 *
 * @author Iker Aramburu, Pau Vico i Steeven Bagner
 */
@Service
public class AuthService {

    // Atributs de la classe
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Constructor default de la classe
     * 
     * @param repo Repositori d'usuari
     * @param passwordEncoder Encriptador de contrasenyes
     * @param jwtService Servei per el token
     */
    public AuthService(UserRepository repo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Funció per realitzar el login
     * 
     * @param email Email de l'usuari
     * @param rawPassword Contrasenya de l'usuari
     * @return Token d'usuari
     */
    public String login(String email, String rawPassword) {
        System.out.println("INTENT DE LOGIN DE: " + email);
        System.out.println("PASSWORD: [" + rawPassword + "]");
        UserAccount user = repo.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(rawPassword, user.getContrasenya())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  
        System.out.println("PASSWORD GUARDAT: " + user.getContrasenya());
        System.out.println("COINCIDEIX: " + passwordEncoder.matches(rawPassword, user.getContrasenya()));
                
        if (!passwordEncoder.matches(rawPassword, user.getContrasenya())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return jwtService.issueToken(
                String.valueOf(user.getId()),
                user.getNom(),
                user.getRoles().stream().toList()
        );
    }
}
