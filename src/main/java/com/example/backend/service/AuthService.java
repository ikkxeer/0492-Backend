package com.example.backend.service;

import com.example.backend.domain.UserAccount;
import com.example.backend.repo.UserRepository;
import com.example.backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

        public AuthService(UserRepository repo,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String rawPassword) {
        System.out.println("LOGIN attempt: " + email);
        System.out.println("Password recibido: [" + rawPassword + "]");
        UserAccount user = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(rawPassword, user.getContrasenya())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
        
 
    System.out.println("Stored password: " + user.getContrasenya());
    System.out.println("Matches: " + passwordEncoder.matches(rawPassword, user.getContrasenya()));
                
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
