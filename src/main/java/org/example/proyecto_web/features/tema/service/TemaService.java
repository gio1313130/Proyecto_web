package org.example.proyecto_web.features.tema.service;


import org.example.proyecto_web.core.entidades.Tema;
import org.example.proyecto_web.features.tema.dto.TemaRequestDTO;
import org.example.proyecto_web.features.tema.dto.TemaResponseDTO;

import java.util.List;

public interface TemaService {
    List<TemaResponseDTO> findAll();
    TemaResponseDTO findById(Long id);
    TemaResponseDTO save(TemaRequestDTO temaRequestDTO);
    TemaResponseDTO update(Long id, TemaRequestDTO temaRequestDTO);
    void deleteById(Long id);
    List<TemaResponseDTO> findByMateriaId(Long idMateria);
}
