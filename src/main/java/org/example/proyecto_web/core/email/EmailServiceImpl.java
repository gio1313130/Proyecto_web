package org.example.proyecto_web.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarCorreoBienvenida(String destinatario, String nombreUsuario) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destinatario);
        mensaje.setSubject("Bienvenido a la plataforma educativa");
        mensaje.setText(
                "Hola " + nombreUsuario + ",\n\n" +
                        "Tu cuenta fue registrada correctamente.\n" +
                        "Ya puedes acceder a los materiales, recursos y cuestionarios disponibles.\n\n" +
                        "Saludos,\n" +
                        "Equipo de la plataforma GG"
        );

        javaMailSender.send(mensaje);
    }

    @Override
    public void enviarCorreoResultado(
            String destinatario,
            String nombreUsuario,
            String tituloCuestionario,
            Integer puntaje,
            Integer totalPreguntas,
            Integer correctas
    ) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destinatario);
        mensaje.setSubject("Resultado de cuestionario: " + tituloCuestionario);
        mensaje.setText(
                "Hola " + nombreUsuario + ",\n\n" +
                        "Tu intento del cuestionario \"" + tituloCuestionario + "\" fue calificado correctamente.\n\n" +
                        "Resultado:\n" +
                        "Puntaje: " + puntaje + "\n" +
                        "Correctas: " + correctas + " de " + totalPreguntas + "\n\n" +
                        "Sigue estudiando los recursos del tema para mejorar tu desempeño.\n\n" +
                        "Saludos,\n" +
                        "Equipo de la plataforma GG"
        );

        javaMailSender.send(mensaje);
    }
}
