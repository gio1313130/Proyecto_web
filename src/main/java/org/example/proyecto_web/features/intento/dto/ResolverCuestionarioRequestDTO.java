package org.example.proyecto_web.features.intento.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResolverCuestionarioRequestDTO {

    private Long idUsuario;
    private Long idCuestionario;
    private List<RespuestaRequestDTO> respuestas;
}