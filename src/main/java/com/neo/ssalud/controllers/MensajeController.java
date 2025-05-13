package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.MensajeDTO;
import com.neo.ssalud.services.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensaje")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeService mensajeService;

    @PostMapping("/enviar")
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO mensajeDTO) {
        // Aquí deberías validar que el emisor es médico en el servicio
        MensajeDTO mensajeCreado = mensajeService.enviarMensaje(mensajeDTO);
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
