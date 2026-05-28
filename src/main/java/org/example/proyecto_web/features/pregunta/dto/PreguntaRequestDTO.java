package org.example.proyecto_web.features.pregunta.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreguntaRequestDTO {
    private String enunciado;
    private Long idCuestionario;
}
