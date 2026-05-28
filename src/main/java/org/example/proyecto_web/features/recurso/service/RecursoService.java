package org.example.proyecto_web.features.recurso.service;

import org.example.proyecto_web.features.recurso.dto.RecursoRequestDTO;
import org.example.proyecto_web.features.recurso.dto.RecursoResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecursoService {

    List<RecursoResponseDTO> findAll();

    RecursoResponseDTO findById(Long id);

    List<RecursoResponseDTO> findByTemaId(Long idTema);

    RecursoResponseDTO upload(
            MultipartFile file,
            String tituloRecurso,
            String tipoRecurso,
            String autor,
            String descripcionRecurso,
            Long idTema
    );

    RecursoResponseDTO update(Long id, RecursoRequestDTO recursoRequestDTO);

    void deleteById(Long id);
}