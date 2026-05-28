package org.example.proyecto_web.features.recurso.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecursoRequestDTO {

    private String tituloRecurso;
    private String tipoRecurso;
    private String autor;
    private String descripcionRecurso;
    private Long idTema;
}