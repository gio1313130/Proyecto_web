package org.example.proyecto_web.features.pregunta.service.impl;

import org.example.proyecto_web.core.entidades.Cuestionario;
import org.example.proyecto_web.core.entidades.Pregunta;
import org.example.proyecto_web.features.cuestionario.repository.CuestionarioRepository;
import org.example.proyecto_web.features.pregunta.dto.PreguntaRequestDTO;
import org.example.proyecto_web.features.pregunta.dto.PreguntaResponseDTO;
import org.example.proyecto_web.features.pregunta.repository.PreguntaRepository;
import org.example.proyecto_web.features.pregunta.service.PreguntaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreguntaServiceImpl implements PreguntaService {
    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponseDTO> findAll() {
        return preguntaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PreguntaResponseDTO findById(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + id));

        return toResponseDTO(pregunta);
    }

    @Override
    @Transactional
    public PreguntaResponseDTO save(PreguntaRequestDTO preguntaRequestDTO) {
        Cuestionario cuestionario = cuestionarioRepository.findById(preguntaRequestDTO.getIdCuestionario())
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + preguntaRequestDTO.getIdCuestionario()));

        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(preguntaRequestDTO.getEnunciado());
        pregunta.setCuestionario(cuestionario);

        Pregunta preguntaGuardado = preguntaRepository.save(pregunta);

        return toResponseDTO(preguntaGuardado);
    }

    @Override
    @Transactional
    public PreguntaResponseDTO update(Long id, PreguntaRequestDTO preguntaRequestDTO) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + id));

        Cuestionario cuestionario = cuestionarioRepository.findById(preguntaRequestDTO.getIdCuestionario())
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + preguntaRequestDTO.getIdCuestionario()));

        pregunta.setEnunciado(preguntaRequestDTO.getEnunciado());
        pregunta.setCuestionario(cuestionario);

        Pregunta preguntaActualizado = preguntaRepository.save(pregunta);

        return toResponseDTO(preguntaActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new RuntimeException("Pregunta no encontrada con id: " + id);
        }

        preguntaRepository.deleteById(id);
    }
    private PreguntaResponseDTO toResponseDTO(Pregunta pregunta) {
        return new PreguntaResponseDTO(
                pregunta.getIdPregunta(),
                pregunta.getEnunciado(),
                pregunta.getCuestionario().getIdCuestionario(),
                pregunta.getCuestionario().getTituloCuestionario()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaResponseDTO> findByCuestionarioId(Long idCuestionario) {
        return preguntaRepository.findByCuestionario_IdCuestionario(idCuestionario)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }



}





