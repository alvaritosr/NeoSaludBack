package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.MensajeDTO;
import com.neo.ssalud.security.JWTService;
import com.neo.ssalud.services.MensajeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensaje")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;
    private final JWTService jwtService;

    @PostMapping("/enviar")
    public ResponseEntity<MensajeDTO> enviarMensaje(HttpServletRequest request,
                                                    @RequestBody MensajeDTO mensajeDTO) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token no presente o no válido en la cabecera Authorization");
        }
        String token = authHeader.substring(7);
        Long idEmisor = jwtService.extractTokenData(token).getId();

        MensajeDTO mensajeCreado = mensajeService.enviarMensaje(idEmisor, mensajeDTO);
        return mensajeCreado != null ? ResponseEntity.ok(mensajeCreado) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/chat/{idChat}")
    public ResponseEntity<List<MensajeDTO>> obtenerMensajesPorChat(@PathVariable Long idChat) {
        List<MensajeDTO> mensajes = mensajeService.obtenerMensajesPorChat(idChat);
        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<List<MensajeDTO>> obtenerMensajesEnviadosPorMedico(@PathVariable Long idMedico) {
        List<MensajeDTO> mensajes = mensajeService.obtenerMensajesEnviadosPorMedico(idMedico);
        return ResponseEntity.ok(mensajes);
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<MensajeDTO>> obtenerMensajesRecibidosPorPaciente(@PathVariable Long idPaciente) {
        List<MensajeDTO> mensajes = mensajeService.obtenerMensajesRecibidosPorPaciente(idPaciente);
        return ResponseEntity.ok(mensajes);
    }
}
