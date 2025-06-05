package com.neo.ssalud.controllers;

import com.neo.ssalud.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping(value = "/enviar", produces = "text/plain")
    public ResponseEntity<String> enviarCorreo(
            @RequestParam String destinatario,
            @RequestParam String asunto,
            @RequestParam String contenido) {
        emailService.enviarCorreo(destinatario, asunto, contenido);
        return ResponseEntity.ok("Correo enviado exitosamente");
    }
}