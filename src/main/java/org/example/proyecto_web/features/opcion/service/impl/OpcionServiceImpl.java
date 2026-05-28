package org.example.proyecto_web.features.opcion.service.impl;
import org.example.proyecto_web.core.entidades.Opcion;
import org.example.proyecto_web.core.entidades.Pregunta;
import org.example.proyecto_web.features.opcion.dto.OpcionRequestDTO;
import org.example.proyecto_web.features.opcion.dto.OpcionResponseDTO;
import org.example.proyecto_web.features.opcion.repository.OpcionRepository;
import org.example.proyecto_web.features.opcion.service.OpcionService;

import org.example.proyecto_web.features.pregunta.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpcionServiceImpl implements OpcionService {
    @Autowired
    private OpcionRepository opcionRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;


    @Override
    @Transactional(readOnly = true)
    public List<OpcionResponseDTO> findAll() {
        return opcionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OpcionResponseDTO findById(Long id) {
        Opcion opcion = opcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opcion no encontrada con id: " + id));

        return toResponseDTO(opcion);
    }

    @Override
    @Transactional
    public OpcionResponseDTO save(OpcionRequestDTO opcionRequestDTO) {
        Pregunta pregunta = preguntaRepository.findById(opcionRequestDTO.getIdPregunta())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + opcionRequestDTO.getIdPregunta()));


        if (Boolean.TRUE.equals(opcionRequestDTO.getEsCorrecta())) {
            boolean yaExisteCorrecta = opcionRepository.existsByPregunta_IdPreguntaAndEsCorrectaTrue(
                    opcionRequestDTO.getIdPregunta()
            );

            if (yaExisteCorrecta) {
                throw new RuntimeException("Esta pregunta ya tiene una opción correcta");
            }
        }

        Opcion opcion = new Opcion();
        opcion.setTextoOpcion(opcionRequestDTO.getTextoOpcion());
        opcion.setEsCorrecta(opcionRequestDTO.getEsCorrecta());
        opcion.setPregunta(pregunta);

        Opcion opcionGuardado = opcionRepository.save(opcion);

        return toResponseDTO(opcionGuardado);
    }

    @Override
    @Transactional
    public OpcionResponseDTO update(Long id, OpcionRequestDTO opcionRequestDTO) {
        Opcion opcion = opcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opcion no encontrada con id: " + id));

        Pregunta pregunta = preguntaRepository.findById(opcionRequestDTO.getIdPregunta())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con id: " + opcionRequestDTO.getIdPregunta()));

        if (Boolean.TRUE.equals(opcionRequestDTO.getEsCorrecta())) {
            boolean yaExisteOtraCorrecta = opcionRepository.existsByPregunta_IdPreguntaAndEsCorrectaTrueAndIdOpcionNot(
                    opcionRequestDTO.getIdPregunta(),
                    id
            );

            if (yaExisteOtraCorrecta) {
                throw new RuntimeException("Esta pregunta ya tiene otra opción correcta");
            }
        }
        opcion.setTextoOpcion(opcionRequestDTO.getTextoOpcion());
        opcion.setEsCorrecta(opcionRequestDTO.getEsCorrecta());
        opcion.setPregunta(pregunta);

        Opcion opcionActualizado = opcionRepository.save(opcion);

        return toResponseDTO(opcionActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!opcionRepository.existsById(id)) {
            throw new RuntimeException("Opcion no encontrada con id: " + id);
        }

        opcionRepository.deleteById(id);
    }

    private OpcionResponseDTO toResponseDTO(Opcion opcion) {
        return new OpcionResponseDTO(
                opcion.getIdOpcion(),
                opcion.getTextoOpcion(),
                opcion.getEsCorrecta(),
                opcion.getPregunta().getIdPregunta(),
                opcion.getPregunta().getEnunciado()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpcionResponseDTO> findByPreguntaId(Long idPregunta) {
        return opcionRepository.findByPregunta_IdPregunta(idPregunta)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}

