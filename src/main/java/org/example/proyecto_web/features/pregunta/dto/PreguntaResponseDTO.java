package org.example.proyecto_web.features.pregunta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaResponseDTO {
    private Long idPregunta;
    private String enunciado;

    private Long idCuestionario;
    private String tituloCuestionario;
}
