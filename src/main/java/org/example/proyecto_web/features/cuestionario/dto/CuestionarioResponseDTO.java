package org.example.proyecto_web.features.cuestionario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuestionarioResponseDTO {

    private Long idCuestionario;
    private String tituloCuestionario;
    private String dificultad;

    private Long idTema;
    private String nombreTema;

    private Long idMateria;
    private String nombreMateria;
}
