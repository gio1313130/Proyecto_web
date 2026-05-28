package org.example.proyecto_web.features.intento.service.impl;

import org.example.proyecto_web.core.email.EmailService;
import org.example.proyecto_web.core.entidades.*;
import org.example.proyecto_web.features.cuestionario.repository.CuestionarioRepository;
import org.example.proyecto_web.features.intento.dto.*;
import org.example.proyecto_web.features.intento.repository.IntentoRepository;
import org.example.proyecto_web.features.intento.service.IntentoService;
import org.example.proyecto_web.features.opcion.repository.OpcionRepository;
import org.example.proyecto_web.features.pregunta.repository.PreguntaRepository;
import org.example.proyecto_web.features.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IntentoServiceImpl implements IntentoService {
    @Autowired
    private IntentoRepository intentoRepository;

    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<IntentoResponseDTO> findAll() {
        return intentoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IntentoResponseDTO findById(Long id) {
        Intento intento = intentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Intento no encontrado"));

        return toResponseDTO(intento);
    }

    @Override
    @Transactional
    public IntentoResponseDTO save(IntentoRequestDTO intentoRequestDTO) {
        Cuestionario cuestionario = cuestionarioRepository.findById(intentoRequestDTO.getIdCuestionario())
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado"));

        Usuario usuario = usuarioRepository.findById(intentoRequestDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Intento intento = new Intento();
        intento.setPuntaje(intentoRequestDTO.getPuntaje());
        intento.setCuestionario(cuestionario);
        intento.setUsuario(usuario);

        Intento intentoGuardado = intentoRepository.save(intento);

        return toResponseDTO(intentoGuardado);
    }

    private IntentoResponseDTO toResponseDTO(Intento intento) {
        return new IntentoResponseDTO(
                intento.getIdIntento(),
                intento.getFechaRealizacion(),
                intento.getPuntaje(),

                intento.getCuestionario().getIdCuestionario(),
                intento.getCuestionario().getTituloCuestionario(),
                intento.getCuestionario().getDificultad(),

                intento.getCuestionario().getTema().getIdTema(),
                intento.getCuestionario().getTema().getNombreTema(),

                intento.getCuestionario().getTema().getMateria().getIdMateria(),
                intento.getCuestionario().getTema().getMateria().getNombreMateria(),

                intento.getUsuario().getIdUsuario(),
                intento.getUsuario().getNombreUsuario(),
                intento.getUsuario().getCorreo(),
                intento.getUsuario().getRol()
        );
    }


    @Override
    @Transactional
    public ResolverCuestionarioResponseDTO resolverCuestionario(ResolverCuestionarioRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + requestDTO.getIdUsuario()));

        Cuestionario cuestionario = cuestionarioRepository.findById(requestDTO.getIdCuestionario())
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con id: " + requestDTO.getIdCuestionario()));

        List<Pregunta> preguntas = preguntaRepository.findByCuestionario_IdCuestionario(
                requestDTO.getIdCuestionario()
        );

        int totalPreguntas = preguntas.size();

        if (totalPreguntas == 0) {
            throw new RuntimeException("El cuestionario no tiene preguntas");
        }

        int correctas = 0;

        for (Pregunta pregunta : preguntas) {
            Long idPregunta = pregunta.getIdPregunta();

            RespuestaRequestDTO respuestaUsuario = requestDTO.getRespuestas()
                    .stream()
                    .filter(respuesta -> respuesta.getIdPregunta().equals(idPregunta))
                    .findFirst()
                    .orElse(null);

            if (respuestaUsuario == null) {
                continue;
            }

            Opcion opcionSeleccionada = opcionRepository.findById(respuestaUsuario.getIdOpcion())
                    .orElseThrow(() -> new RuntimeException("Opción no encontrada con id: " + respuestaUsuario.getIdOpcion()));

            if (!opcionSeleccionada.getPregunta().getIdPregunta().equals(idPregunta)) {
                throw new RuntimeException("La opción seleccionada no pertenece a la pregunta con id: " + idPregunta);
            }

            if (Boolean.TRUE.equals(opcionSeleccionada.getEsCorrecta())) {
                correctas++;
            }
        }

        int puntaje = (int) Math.round((correctas * 100.0) / totalPreguntas);

        Intento intento = new Intento();
        intento.setUsuario(usuario);
        intento.setCuestionario(cuestionario);
        intento.setPuntaje(puntaje);

        Intento intentoGuardado = intentoRepository.save(intento);

        try {
            emailService.enviarCorreoResultado(
                    usuario.getCorreo(),
                    usuario.getNombreUsuario(),
                    cuestionario.getTituloCuestionario(),
                    puntaje,
                    totalPreguntas,
                    correctas
            );
        } catch (Exception e) {
            System.out.println("No se pudo enviar correo de resultado: " + e.getMessage());
        }

        return new ResolverCuestionarioResponseDTO(
                intentoGuardado.getIdIntento(),
                puntaje,
                totalPreguntas,
                correctas,
                "Cuestionario calificado correctamente"
        );
    }
    @Override
    @Transactional(readOnly = true)
    public List<IntentoResponseDTO> findByUsuarioId(Long idUsuario) {
        return intentoRepository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
