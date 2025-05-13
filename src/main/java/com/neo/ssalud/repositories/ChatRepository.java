package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.medico.id = :id OR c.paciente.id = :id")
    List<Chat> findChatsByUserId(@Param("id") Long id);

    // Para obtener chats por médico
    List<Chat> findChatsByMedicoId(Long idMedico);

    // Para obtener chats por paciente
    List<Chat> findChatsByPacienteId(Long idPaciente);
}

