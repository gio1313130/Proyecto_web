package org.example.proyecto_web.features.auth.service.impl;

import org.example.proyecto_web.config.security.JwtService;
import org.example.proyecto_web.core.entidades.Usuario;
import org.example.proyecto_web.features.auth.dto.LoginRequestDTO;
import org.example.proyecto_web.features.auth.dto.LoginResponseDTO;
import org.example.proyecto_web.features.auth.service.AuthService;
import org.example.proyecto_web.features.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.proyecto_web.features.auth.dto.AuthUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Usuario usuario = usuarioRepository.findByCorreo(loginRequestDTO.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        boolean passwordValida = passwordEncoder.matches(
                loginRequestDTO.getPassword(),
                usuario.getPasswordUsuario()
        );

        if (!passwordValida) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtService.generarToken(usuario);

        return new LoginResponseDTO(
                token,
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }

    @Override
    public AuthUserDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario usuario)) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return new AuthUserDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }

}