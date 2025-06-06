package com.neo.ssalud.services;

import com.neo.ssalud.dto.MensajeDTO;
import com.neo.ssalud.exception.RecursoNoEncontrado;
import com.neo.ssalud.models.Chat;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Mensaje;
import com.neo.ssalud.repositories.ChatRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.mensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class MensajeService {

    private final mensajeRepository mensajeRepository;
    private final ChatRepository chatRepository;
    private final medicoRepository medicoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MensajeDTO enviarMensaje(Long idEmisor, MensajeDTO mensajeDTO) {
        Medico emisor = medicoRepository.findById(idEmisor)
                .orElseThrow(() -> new RecursoNoEncontrado("Médico emisor no encontrado con id: " + idEmisor));

        Medico receptor = medicoRepository.findById(mensajeDTO.getIdReceptor())
                .orElseThrow(() -> new RecursoNoEncontrado("Medico receptor no encontrado"));

        Chat chat = chatRepository.findById(mensajeDTO.getIdChat())
                .orElseThrow(() -> new RecursoNoEncontrado("Chat no encontrado"));

        if (mensajeDTO.getContenido() == null || mensajeDTO.getContenido().isEmpty()) {
            throw new IllegalArgumentException("El campo 'contenido' no puede ser nulo o vacío");
        }

        if (emisor.equals(receptor)) {
            throw new IllegalArgumentException("No se puede enviar mensaje a uno mismo");
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setContenido(mensajeDTO.getContenido());
        mensaje.setFecha(LocalDateTime.now());

        mensajeRepository.save(mensaje);

        MensajeDTO mensajeGuardado = convertirAMensajeDTO(mensaje);

        // Enviar mensaje vía WebSocket al topic del chat
        messagingTemplate.convertAndSend("/topic/chat/" + mensajeGuardado.getIdChat(), mensajeGuardado);

        return mensajeGuardado;
    }

    public List<MensajeDTO> obtenerMensajesPorChat(Long idChat) {
        List<Mensaje> mensajes = mensajeRepository.findMessagesByChatId(idChat);
        if (mensajes.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron mensajes para el chat con ID " + idChat);
        }
        return mensajes.stream()
                .map(this::convertirAMensajeDTO)
                .collect(Collectors.toList());
    }

    public List<MensajeDTO> obtenerMensajesEnviados(Long idMedico) {
        List<Mensaje> mensajes = mensajeRepository.findSentMessages(idMedico);
        if (mensajes.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron mensajes enviados por el médico con ID " + idMedico);
        }
        return mensajes.stream()
                .map(this::convertirAMensajeDTO)
                .collect(Collectors.toList());
    }

    public List<MensajeDTO> obtenerMensajesRecibidos(Long idPaciente) {
        List<Mensaje> mensajes = mensajeRepository.findReceivedMessages(idPaciente);
        if (mensajes.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron mensajes recibidos por el paciente con ID " + idPaciente);
        }
        return mensajes.stream()
                .map(this::convertirAMensajeDTO)
                .collect(Collectors.toList());
    }

    public List<MensajeDTO> obtenerMensajesEnviadosPorMedico(Long idMedico) {
        List<Mensaje> mensajes = mensajeRepository.findSentMessagesByMedicoId(idMedico);
        if (mensajes.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron mensajes enviados por el médico con ID " + idMedico);
        }
        return mensajes.stream().map(this::convertirAMensajeDTO).collect(Collectors.toList());
    }

    public List<MensajeDTO> obtenerMensajesRecibidosPorPaciente(Long idPaciente) {
        List<Mensaje> mensajes = mensajeRepository.findReceivedMessagesByPacienteId(idPaciente);
        if (mensajes.isEmpty()) {
            throw new RecursoNoEncontrado("No se encontraron mensajes recibidos por el paciente con ID " + idPaciente);
        }
        return mensajes.stream().map(this::convertirAMensajeDTO).collect(Collectors.toList());
    }

    private MensajeDTO convertirAMensajeDTO(Mensaje mensaje) {
        MensajeDTO dto = new MensajeDTO();
        dto.setId(mensaje.getId());
        dto.setIdChat(mensaje.getChat().getId());
        dto.setIdReceptor(mensaje.getReceptor().getId());
        dto.setIdEmisor(mensaje.getEmisor().getId());
        dto.setContenido(mensaje.getContenido());
        dto.setFecha(mensaje.getFecha().toString());
        return dto;
    }
}
