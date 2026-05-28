package org.example.proyecto_web.features.opcion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OpcionResponseDTO {

    private Long idOpcion;
    private String textoOpcion;
    private Boolean esCorrecta;
    private Long idPregunta;
    private String enunciado;

}
