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
public class PreguntaResolverDTO {

    private Long idPregunta;
    private String enunciado;
    private List<OpcionResolverDTO> opciones;
}