package org.example.proyecto_web.features.cuestionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuestionarioResolverDTO {

    private Long idCuestionario;
    private String tituloCuestionario;
    private String dificultad;
    private List<PreguntaResolverDTO> preguntas;
}