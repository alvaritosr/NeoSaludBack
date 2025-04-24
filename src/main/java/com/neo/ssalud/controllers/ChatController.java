package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.ChatDTO;
import com.neo.ssalud.dto.CrearChatDTO;
import com.neo.ssalud.exception.RecursoNoEncontrado;
import com.neo.ssalud.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/crear")
    public ResponseEntity<CrearChatDTO> crearChat(@RequestBody CrearChatDTO crearChatDTO) {
        CrearChatDTO chatCreado = chatService.crearChat(crearChatDTO);
        return chatCreado != null ? ResponseEntity.ok(chatCreado) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/medico/{idMedico}")
    public ResponseEntity<?> getChatsByMedico(@PathVariable Long idMedico) {
        try {
            List<ChatDTO> chats = chatService.getChatsByMedico(idMedico);
            return ResponseEntity.ok(chats);
        } catch (RecursoNoEncontrado e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<ChatDTO>> getChatsByPaciente(@PathVariable Long idPaciente) {
        List<ChatDTO> chats = chatService.getChatsByPaciente(idPaciente);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/detalles/{idChat}")
    public ResponseEntity<ChatDTO> getDetallesChat(@PathVariable Long idChat) {
        ChatDTO chat = chatService.getDetallesChat(idChat);
        return chat != null ? ResponseEntity.ok(chat) : ResponseEntity.notFound().build();
    }
}
