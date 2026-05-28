package org.example.proyecto_web.features.cuestionario.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CuestionarioRequestDTO {

    private String tituloCuestionario;
    private String dificultad;
    private Long idTema;

}
