package com.example.backend;

import com.example.backend.repo.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Classe temporal para migrar las contraseñas a ser cifradas a la base de datos
 * en caso que no se hiciese no podriamos hacer el login y demás
 * 
 * @author samui
 */
@Component
public class MigradorContrasenyes implements ApplicationRunner {
    // Atributs de la classe
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor por defecto de MigradorContrasenyes
     * 
     * @param userRepository Repositorio de usuarios
     * @param passwordEncoder Encriptador de contrasenyes
     */
    public MigradorContrasenyes(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Al ejecutarse, modifica la contraseña del usuario a una encripada con passwordEncoder
    @Override
    public void run(ApplicationArguments args) {
        userRepository.findAll().forEach(user -> {
            if (!user.getContrasenya().startsWith("$2a$")) {
                user.setContrasenya(passwordEncoder.encode(user.getContrasenya()));
                userRepository.save(user);
                System.out.println("Usuario Migrado: " + user.getEmail());
            }
        });
    }
}