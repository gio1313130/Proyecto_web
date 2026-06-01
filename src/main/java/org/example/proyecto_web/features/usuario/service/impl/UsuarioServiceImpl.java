package org.example.proyecto_web.features.usuario.service.impl;

import org.example.proyecto_web.core.email.EmailService;
import org.example.proyecto_web.core.entidades.Usuario;
import org.example.proyecto_web.features.usuario.dto.UsuarioRequestDTO;
import org.example.proyecto_web.features.usuario.dto.UsuarioResponseDTO;
import org.example.proyecto_web.features.usuario.repository.UsuarioRepository;
import org.example.proyecto_web.features.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        return toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.existsByCorreo(usuarioRequestDTO.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario registrado con ese correo");
        }

        Usuario usuario = new Usuario();

        usuario.setNombreUsuario(usuarioRequestDTO.getNombreUsuario());
        usuario.setCorreo(usuarioRequestDTO.getCorreo());
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioRequestDTO.getPasswordUsuario()));

        if (usuarioRequestDTO.getRol() == null || usuarioRequestDTO.getRol().isBlank()) {
            usuario.setRol("ALUMNO");
        } else {
            usuario.setRol(usuarioRequestDTO.getRol());
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        try {
            emailService.enviarCorreoBienvenida(
                    usuarioGuardado.getCorreo(),
                    usuarioGuardado.getNombreUsuario()
            );
        } catch (Exception e) {
            System.out.println("No se pudo enviar correo de bienvenida: " + e.getMessage());
        }

        return toResponseDTO(usuarioGuardado);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (usuarioRepository.existsByCorreoAndIdUsuarioNot(usuarioRequestDTO.getCorreo(), id)) {
            throw new RuntimeException("Ya existe otro usuario registrado con ese correo");
        }

        usuario.setNombreUsuario(usuarioRequestDTO.getNombreUsuario());
        usuario.setCorreo(usuarioRequestDTO.getCorreo());

        if (usuarioRequestDTO.getRol() == null || usuarioRequestDTO.getRol().isBlank()) {
            usuario.setRol("ALUMNO");
        } else {
            usuario.setRol(usuarioRequestDTO.getRol());
        }

        if (usuarioRequestDTO.getPasswordUsuario() != null && !usuarioRequestDTO.getPasswordUsuario().isBlank()) {
            usuario.setPasswordUsuario(passwordEncoder.encode(usuarioRequestDTO.getPasswordUsuario()));
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return toResponseDTO(usuarioActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }

        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombreUsuario(),
                usuario.getCorreo(),
                usuario.getRol()
        );
    }
}
