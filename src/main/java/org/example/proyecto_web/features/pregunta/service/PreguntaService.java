package org.example.proyecto_web.features.pregunta.service;

import org.example.proyecto_web.features.pregunta.dto.PreguntaRequestDTO;
import org.example.proyecto_web.features.pregunta.dto.PreguntaResponseDTO;


import java.util.List;

public interface PreguntaService {
    List<PreguntaResponseDTO> findAll();
    PreguntaResponseDTO findById(Long id);
    PreguntaResponseDTO save(PreguntaRequestDTO preguntaRequestDTO);
    PreguntaResponseDTO update(Long id, PreguntaRequestDTO preguntaRequestDTO);
    void deleteById(Long id);
    List<PreguntaResponseDTO> findByCuestionarioId(Long idCuestionario);

}
