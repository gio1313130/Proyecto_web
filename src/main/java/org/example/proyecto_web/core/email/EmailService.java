package org.example.proyecto_web.core.email;

public interface EmailService {

    void enviarCorreoBienvenida(String destinatario, String nombreUsuario);

    void enviarCorreoResultado(
            String destinatario,
            String nombreUsuario,
            String tituloCuestionario,
            Integer puntaje,
            Integer totalPreguntas,
            Integer correctas
    );
}
