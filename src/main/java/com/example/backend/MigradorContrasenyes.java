package com.example.backend;

import com.example.backend.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MigradorContrasenyes implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MigradorContrasenyes(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        userRepository.findAll().forEach(user -> {
            if (!user.getContrasenya().startsWith("$2a$")) {
                user.setContrasenya(passwordEncoder.encode(user.getContrasenya()));
                userRepository.save(user);
                System.out.println("✅ Migrado: " + user.getEmail());
            }
        });
        System.out.println("✅ Migración completada");
    }
}