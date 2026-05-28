package org.example.proyecto_web.features.opcion.service;




import org.example.proyecto_web.features.opcion.dto.OpcionRequestDTO;
import org.example.proyecto_web.features.opcion.dto.OpcionResponseDTO;

import java.util.List;

public interface OpcionService {
    List<OpcionResponseDTO> findAll();
    OpcionResponseDTO findById(Long id);
    OpcionResponseDTO save(OpcionRequestDTO opcionRequestDTO);
    OpcionResponseDTO update(Long id, OpcionRequestDTO opcionRequestDTO);
    void deleteById(Long id);
    List<OpcionResponseDTO> findByPreguntaId(Long idPregunta);
}
