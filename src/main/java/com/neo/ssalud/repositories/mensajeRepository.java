package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface mensajeRepository extends JpaRepository<Mensaje, Long> {
    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :idChat ORDER BY m.fecha ASC")
    List<Mensaje> findMessagesByChatId(@Param("idChat") Long idChat);

    @Query("SELECT m FROM Mensaje m WHERE m.emisor.id = :idMedico ORDER BY m.fecha ASC")
    List<Mensaje> findSentMessages(@Param("idMedico") Long idMedico);

    @Query("SELECT m FROM Mensaje m WHERE m.receptor.id = :idPaciente ORDER BY m.fecha ASC")
    List<Mensaje> findReceivedMessages(@Param("idPaciente") Long idPaciente);

    @Query("SELECT m FROM Mensaje m WHERE m.emisor.id = :idMedico")
    List<Mensaje> findSentMessagesByMedicoId(@Param("idMedico") Long idMedico);

    @Query("SELECT m FROM Mensaje m WHERE m.receptor.id = :idPaciente")
    List<Mensaje> findReceivedMessagesByPacienteId(@Param("idPaciente") Long idPaciente);
}