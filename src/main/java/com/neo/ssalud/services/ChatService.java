package com.neo.ssalud.services;

import com.neo.ssalud.dto.ChatDTO;
import com.neo.ssalud.dto.CrearChatDTO;
import com.neo.ssalud.exception.RecursoNoEncontrado;
import com.neo.ssalud.models.Chat;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.ChatRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
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
        Medico medico = medicoRepository.findById(crearChatDTO.getId_creador())
                .orElseThrow(() -> new RecursoNoEncontrado("Médico creador no encontrado"));
        Paciente paciente = pacienteRepository.findById(crearChatDTO.getId_receptor())
                .orElseThrow(() -> new RecursoNoEncontrado("Paciente receptor no encontrado"));

        Chat chat = new Chat();
        chat.setMedico(medico);
        chat.setPaciente(paciente);
        chat = chatRepository.save(chat);

        CrearChatDTO dto = new CrearChatDTO();
        dto.setId(chat.getId());
        dto.setId_creador(medico.getId());
        dto.setId_receptor(paciente.getId());
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
     * Obtiene todos los chats asociados a un médico.
     *
     * @param idMedico ID del médico
     * @return lista de chats en forma de DTO
     */
    public List<ChatDTO> getChatsByMedico(Long idMedico) {
        List<Chat> chats = chatRepository.findChatsByMedicoId(idMedico);
        if (chats.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron chats para el médico con ID " + idMedico);
        }
        return chats.stream().map(this::convertirAChatDTO).collect(Collectors.toList());
    }

    /**
     * Obtiene todos los chats asociados a un paciente.
     *
     * @param idPaciente ID del paciente
     * @return lista de chats en forma de DTO
     */
    public List<ChatDTO> getChatsByPaciente(Long idPaciente) {
        List<Chat> chats = chatRepository.findChatsByPacienteId(idPaciente);
        if (chats.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron chats para el paciente con ID " + idPaciente);
        }
        return chats.stream().map(this::convertirAChatDTO).collect(Collectors.toList());
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
        dto.setId_creador(chat.getMedico().getId());
        dto.setId_receptor(chat.getPaciente().getId());
        dto.setNombre_receptor(chat.getPaciente().getNombre());
        return dto;
    }
}
