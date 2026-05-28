package org.example.proyecto_web.features.cuestionario.service;

import org.example.proyecto_web.core.entidades.Cuestionario;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioRequestDTO;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioResolverDTO;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioResponseDTO;

import java.util.List;

public interface CuestionarioService {
    List<CuestionarioResponseDTO> findAll();
    CuestionarioResponseDTO findById(Long id);
    CuestionarioResponseDTO save(CuestionarioRequestDTO cuestionarioRequestDTO);
    CuestionarioResponseDTO update(Long id, CuestionarioRequestDTO cuestionarioRequestDTO);
    void deleteById(Long id);
    CuestionarioResolverDTO obtenerParaResolver(Long id);
    List<CuestionarioResponseDTO> findByTemaId(Long idTema);
}
