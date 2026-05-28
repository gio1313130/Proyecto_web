package org.example.proyecto_web.features.usuario.service;

import org.example.proyecto_web.core.entidades.Usuario;
import org.example.proyecto_web.features.usuario.dto.UsuarioRequestDTO;
import org.example.proyecto_web.features.usuario.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO save(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuarioRequestDTO);

    void deleteById(Long id);
}
