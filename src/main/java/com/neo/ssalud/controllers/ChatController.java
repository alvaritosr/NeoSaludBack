package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.ChatDTO;
import com.neo.ssalud.dto.CrearChatDTO;
import com.neo.ssalud.dto.ParticipanteChatDTO;
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
    public ResponseEntity<List<ChatDTO>> getChatsById(@PathVariable Long idMedico) {
        List<ChatDTO> chats = chatService.getChatById(idMedico);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/detalles/{idChat}")
    public ResponseEntity<ChatDTO> getDetallesChat(@PathVariable Long idChat) {
        ChatDTO chat = chatService.getDetallesChat(idChat);
        return chat != null ? ResponseEntity.ok(chat) : ResponseEntity.notFound().build();
    }

    @GetMapping("/otro-participante/{idChat}/{idMedico}")
    public ResponseEntity<ParticipanteChatDTO> obtenerOtroParticipante(
            @PathVariable Long idChat,
            @PathVariable Long idMedico) {
        ParticipanteChatDTO participante = chatService.obtenerOtroParticipante(idChat, idMedico);
        return ResponseEntity.ok(participante);
    }
}
