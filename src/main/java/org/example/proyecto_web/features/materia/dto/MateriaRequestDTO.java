package org.example.proyecto_web.features.materia.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateriaRequestDTO {
    private String nombreMateria;
    private Integer semestre;
    private String descripcionMateria;
}
