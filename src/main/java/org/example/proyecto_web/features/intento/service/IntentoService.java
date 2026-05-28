package org.example.proyecto_web.features.intento.service;

import org.example.proyecto_web.core.entidades.Intento;
import org.example.proyecto_web.features.intento.dto.IntentoRequestDTO;
import org.example.proyecto_web.features.intento.dto.IntentoResponseDTO;
import org.example.proyecto_web.features.intento.dto.ResolverCuestionarioRequestDTO;
import org.example.proyecto_web.features.intento.dto.ResolverCuestionarioResponseDTO;

import java.util.List;

public interface IntentoService {
    List<IntentoResponseDTO> findAll();
    IntentoResponseDTO findById(Long id);
    IntentoResponseDTO save(IntentoRequestDTO intentoRequestDTO);
    ResolverCuestionarioResponseDTO resolverCuestionario(ResolverCuestionarioRequestDTO requestDTO);
    List<IntentoResponseDTO> findByUsuarioId(Long idUsuario);
}
