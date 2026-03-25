package com.example.backend.service;

import com.example.backend.domain.UserAccount;
import com.example.backend.repo.UserRepository;
import com.example.backend.web.dto.ProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileService {

    private final UserRepository repo;

    public ProfileService(UserRepository repo) {
        this.repo = repo;
    }

    public String profile(String email) {
        return repo.findByEmail(email).orElseThrow().getEmail();
    }
    
    public ProfileDto getMyProfile(String email) {
        UserAccount user = repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return new ProfileDto(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getRoles().stream().toList() 
        );
    }
}
