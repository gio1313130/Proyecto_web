package org.example.proyecto_web.features.cuestionario.service.impl;

import org.example.proyecto_web.core.entidades.Cuestionario;
import org.example.proyecto_web.core.entidades.Opcion;
import org.example.proyecto_web.core.entidades.Pregunta;
import org.example.proyecto_web.core.entidades.Tema;
import org.example.proyecto_web.features.cuestionario.dto.*;
import org.example.proyecto_web.features.cuestionario.repository.CuestionarioRepository;
import org.example.proyecto_web.features.cuestionario.service.CuestionarioService;
import org.example.proyecto_web.features.opcion.repository.OpcionRepository;
import org.example.proyecto_web.features.pregunta.repository.PreguntaRepository;
import org.example.proyecto_web.features.tema.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CuestionarioServiceImpl implements CuestionarioService {
    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CuestionarioResponseDTO> findAll() {
        return cuestionarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public CuestionarioResponseDTO findById(Long id) {
        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + id));

        return toResponseDTO(cuestionario);
    }

    @Override
    @Transactional
    public CuestionarioResponseDTO save(CuestionarioRequestDTO cuestionarioRequestDTO) {
        Tema tema = temaRepository.findById(cuestionarioRequestDTO.getIdTema())
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + cuestionarioRequestDTO.getIdTema()));

        Cuestionario cuestionario = new Cuestionario();
        cuestionario.setTituloCuestionario(cuestionarioRequestDTO.getTituloCuestionario());
        cuestionario.setDificultad(cuestionarioRequestDTO.getDificultad());
        cuestionario.setTema(tema);

        Cuestionario cuestionarioGuardado = cuestionarioRepository.save(cuestionario);

        return toResponseDTO(cuestionarioGuardado);
    }

    @Override
    @Transactional
    public CuestionarioResponseDTO update(Long id, CuestionarioRequestDTO cuestionarioRequestDTO) {
        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + id));

        Tema tema = temaRepository.findById(cuestionarioRequestDTO.getIdTema())
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + cuestionarioRequestDTO.getIdTema()));

        cuestionario.setTituloCuestionario(cuestionarioRequestDTO.getTituloCuestionario());
        cuestionario.setDificultad(cuestionarioRequestDTO.getDificultad());
        cuestionario.setTema(tema);

        Cuestionario cuestionarioActualizado = cuestionarioRepository.save(cuestionario);

        return toResponseDTO(cuestionarioActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!cuestionarioRepository.existsById(id)) {
            throw new RuntimeException("Cuestionario no encontrado con id: " + id);
        }

        cuestionarioRepository.deleteById(id);
    }

    private CuestionarioResponseDTO toResponseDTO(Cuestionario cuestionario) {
        return new CuestionarioResponseDTO(
                cuestionario.getIdCuestionario(),
                cuestionario.getTituloCuestionario(),
                cuestionario.getDificultad(),

                cuestionario.getTema().getIdTema(),
                cuestionario.getTema().getNombreTema(),

                cuestionario.getTema().getMateria().getIdMateria(),
                cuestionario.getTema().getMateria().getNombreMateria()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public CuestionarioResolverDTO obtenerParaResolver(Long id) {
        Cuestionario cuestionario = cuestionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + id));

        List<Pregunta> preguntas = preguntaRepository.findByCuestionario_IdCuestionario(id);

        List<PreguntaResolverDTO> preguntasDTO = preguntas.stream()
                .map(pregunta -> {
                    List<Opcion> opciones = opcionRepository.findByPregunta_IdPregunta(pregunta.getIdPregunta());

                    List<OpcionResolverDTO> opcionesDTO = opciones.stream()
                            .map(opcion -> new OpcionResolverDTO(
                                    opcion.getIdOpcion(),
                                    opcion.getTextoOpcion()
                            ))
                            .toList();

                    return new PreguntaResolverDTO(
                            pregunta.getIdPregunta(),
                            pregunta.getEnunciado(),
                            opcionesDTO
                    );
                })
                .toList();

        return new CuestionarioResolverDTO(
                cuestionario.getIdCuestionario(),
                cuestionario.getTituloCuestionario(),
                cuestionario.getDificultad(),
                preguntasDTO
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuestionarioResponseDTO> findByTemaId(Long idTema) {
        return cuestionarioRepository.findByTema_IdTema(idTema)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
