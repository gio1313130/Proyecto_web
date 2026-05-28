package org.example.proyecto_web.features.intento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResolverCuestionarioResponseDTO {

    private Long idIntento;
    private Integer puntaje;
    private Integer totalPreguntas;
    private Integer correctas;
    private String mensaje;
}