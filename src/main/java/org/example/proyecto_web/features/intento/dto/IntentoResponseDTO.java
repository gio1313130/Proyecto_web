package org.example.proyecto_web.features.intento.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IntentoResponseDTO {

    private  Long idIntento;
    private LocalDateTime fechaRealizacion;
    private Integer puntaje;

    private Long idCuestionario;
    private String tituloCuestionario;
    private String dificultad;

    private Long idTema;
    private String nombreTema;

    private Long idMateria;
    private String nombreMateria;

    private Long idUsuario;
    private String nombreUsuario;
    private String correo;
    private String rol;
}
