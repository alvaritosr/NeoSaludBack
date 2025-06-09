package com.neo.ssalud.services;

import com.neo.ssalud.dto.ChatDTO;
import com.neo.ssalud.dto.CrearChatDTO;
import com.neo.ssalud.exception.RecursoNoEncontrado;
import com.neo.ssalud.models.Chat;
import com.neo.ssalud.dto.ParticipanteChatDTO;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.repositories.ChatRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final medicoRepository medicoRepository;
    private final pacienteRepository pacienteRepository;

    /**
     * Crea un nuevo chat entre un médico y un paciente.
     *
     * @param crearChatDTO datos para la creación del chat
     * @return DTO con los datos del chat creado
     */
    public CrearChatDTO crearChat(CrearChatDTO crearChatDTO) {
        Medico creador = medicoRepository.findById(crearChatDTO.getId_creador())
                .orElseThrow(() -> new RecursoNoEncontrado("Médico creador no encontrado"));
        Medico receptor = medicoRepository.findById(crearChatDTO.getId_receptor())
                .orElseThrow(() -> new RecursoNoEncontrado("Medico receptor no encontrado"));

        Chat chat = new Chat();
        chat.setCreador(creador);
        chat.setReceptor(receptor);
        chat = chatRepository.save(chat);

        CrearChatDTO dto = new CrearChatDTO();
        dto.setId(chat.getId());
        dto.setId_creador(creador.getId());
        dto.setId_receptor(receptor.getId());
        return dto;
    }

    /**
     * Obtiene todos los chats donde participe un usuario (médico o paciente).
     *
     * @param idUsuario ID del usuario
     * @return lista de chats en forma de DTO
     */
    public List<ChatDTO> getChatById(Long idUsuario) {
        List<Chat> chats = chatRepository.findChatsByUserId(idUsuario);
        if (chats.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron chats para el usuario con ID: " + idUsuario);
        }
        return chats.stream().map(this::convertirAChatDTO).collect(Collectors.toList());
    }
    /**
     * Obtiene los detalles de un chat por su ID.
     *
     * @param idChat ID del chat
     * @return detalles del chat en forma de DTO
     */
    public ChatDTO getDetallesChat(Long idChat) {
        Chat chat = chatRepository.findById(idChat)
                .orElseThrow(() -> new RecursoNoEncontrado("Chat no encontrado"));
        return convertirAChatDTO(chat);
    }

    /**
     * Convierte una entidad Chat a su correspondiente DTO.
     *
     * @param chat entidad Chat
     * @return DTO del chat
     */
    private ChatDTO convertirAChatDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setId(chat.getId());
        dto.setId_creador(chat.getCreador().getId());
        dto.setId_receptor(chat.getReceptor().getId());
        dto.setNombre_receptor(chat.getReceptor().getNombre());
        return dto;
    }

    public ParticipanteChatDTO obtenerOtroParticipante(Long idChat, Long idMedico) {
        Chat chat = chatRepository.findById(idChat)
                .orElseThrow(() -> new RecursoNoEncontrado("Chat no encontrado con el id: " + idChat));

        if (chat.getCreador().getId().equals(idMedico)) {
            return new ParticipanteChatDTO(chat.getReceptor().getId(), chat.getReceptor().getNombre());
        } else if (chat.getReceptor().getId().equals(idMedico)) {
            return new ParticipanteChatDTO(chat.getCreador().getId(), chat.getCreador().getNombre());
        } else {
            throw new IllegalArgumentException("El médico no está asociado a este chat.");
        }
    }
}
