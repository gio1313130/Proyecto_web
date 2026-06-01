package org.example.proyecto_web.features.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String correo;
    private String rol;
}