package com.example.backend.web;

import com.example.backend.service.ProfileService;
import com.example.backend.web.dto.ProfileDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ProfileDto me(Authentication auth) {
        //return service.profile(auth.getName());
        return service.getMyProfile(auth.getName());
    }
}
