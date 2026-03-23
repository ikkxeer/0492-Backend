package com.example.backend.service;

import com.example.backend.domain.UserAccount;
import com.example.backend.repo.InMemoryUserRepo;
import com.example.backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final InMemoryUserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(InMemoryUserRepo repo,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String username, String rawPassword) {

        System.out.println("LOGIN attempt: " + username);
    System.out.println("Password recibido: [" + rawPassword + "]");
        UserAccount user = repo.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

 
    System.out.println("Stored password: " + user.getPassword());
    System.out.println("Matches: " + passwordEncoder.matches(rawPassword, user.getPassword()));
                
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return jwtService.issueToken(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().toList()
        );
    }
}
