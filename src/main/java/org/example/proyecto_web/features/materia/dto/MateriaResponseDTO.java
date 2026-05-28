package org.example.proyecto_web.features.materia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MateriaResponseDTO {
    private Long idMateria;
    private String nombreMateria;
    private Integer semestre;
    private String descripcionMateria;
}
