package org.example.proyecto_web.features.materia.service;

import org.example.proyecto_web.core.entidades.Materia;
import org.example.proyecto_web.features.materia.dto.MateriaRequestDTO;
import org.example.proyecto_web.features.materia.dto.MateriaResponseDTO;

import java.util.List;

public interface MateriaService {
    List<MateriaResponseDTO> findAll();
    MateriaResponseDTO findById(Long id);
    MateriaResponseDTO save(MateriaRequestDTO materiaRequestDTO);
    MateriaResponseDTO update(Long id, MateriaRequestDTO materiaRequestDTO);
    void deleteById(Long id);
}
