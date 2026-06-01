package org.example.proyecto_web.features.auth.service;

import org.example.proyecto_web.features.auth.dto.AuthUserDTO;
import org.example.proyecto_web.features.auth.dto.LoginRequestDTO;
import org.example.proyecto_web.features.auth.dto.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    AuthUserDTO getAuthenticatedUser();

}