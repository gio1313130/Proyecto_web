package org.example.proyecto_web.features.recurso.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecursoResponseDTO {

    private Long idRecurso;
    private String tituloRecurso;
    private String tipoRecurso;
    private LocalDateTime fechaPublicacion;
    private String autor;
    private String descripcionRecurso;
    private String url;
    private String publicId;
    private String resourceType;

    private Long idTema;
    private String nombreTema;
}