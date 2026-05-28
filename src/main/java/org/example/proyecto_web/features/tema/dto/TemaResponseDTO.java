package org.example.proyecto_web.features.tema.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemaResponseDTO {
    private Long idTema;
    private String nombreTema;
    private String descripcionTema;

    private Long idMateria;
    private String nombreMateria;
}
