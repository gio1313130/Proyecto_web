package org.example.proyecto_web.features.opcion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpcionRequestDTO {

    private String textoOpcion;
    private Boolean esCorrecta;
    private Long idPregunta;

}
