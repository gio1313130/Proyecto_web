package org.example.proyecto_web.features.debug.controller;

import org.example.proyecto_web.core.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/debug")
public class DebugCorreoController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/correo")
    public String probarCorreo(@RequestParam String destino) {
        try {
            emailService.enviarCorreoBienvenida(destino, "Usuario de prueba");
            return "Correo enviado correctamente a: " + destino;
        } catch (Exception e) {
            return "ERROR al enviar correo: " + e.getClass().getName() + " - " + e.getMessage();
        }
    }
}