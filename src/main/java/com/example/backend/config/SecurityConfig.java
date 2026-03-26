package com.example.backend.config;

import com.example.backend.security.JwtAuthFilter;
import com.example.backend.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Serveix per encriptar les contrasenyes
     * 
     * @return Encriptador de contrasenyes
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                // Estáticos (Spring Boot sirve /static automáticamente)
                .requestMatchers(
                        "/", "/index.html",
                        "/login.html", "/profile.html", "/hello.html",
                        "/favicon.ico",
                        "/*.css", "/*.js",
                        "/css/**", "/js/**", "/images/**", "/webjars/**"
                ).permitAll()
                //restringimos acceso a ruta estatica admin
                .requestMatchers("/admin.html").hasRole("ADMIN")
                // Auth público
                .requestMatchers("/api/auth/**").permitAll()
                // Users público
                .requestMatchers("/api/users/**").permitAll()
                // Pale público
                .requestMatchers("/api/pales/**").permitAll()
                // Grup mozos público
                .requestMatchers("/api/grupmozos/**").permitAll()
                // Grup mozos público
                .requestMatchers("/api/client/**").permitAll()
                // Resto protegido
                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
