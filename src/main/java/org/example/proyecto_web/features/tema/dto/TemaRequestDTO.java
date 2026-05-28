package org.example.proyecto_web.features.tema.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TemaRequestDTO {

    private String nombreTema;
    private String descripcionTema;
    private Long idMateria;
}
