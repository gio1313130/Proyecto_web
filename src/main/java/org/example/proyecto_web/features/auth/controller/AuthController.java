package org.example.proyecto_web.features.auth.controller;

import org.example.proyecto_web.features.auth.dto.LoginRequestDTO;
import org.example.proyecto_web.features.auth.dto.LoginResponseDTO;
import org.example.proyecto_web.features.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.proyecto_web.features.auth.dto.AuthUserDTO;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AuthUserDTO me() {
        return authService.getAuthenticatedUser();
    }

}