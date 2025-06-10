package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrescripcionRepository extends JpaRepository<Prescripcion, Long> {
    @Query(value = "SELECT * FROM neosalud.prescripcion WHERE id = :prescripcionId", nativeQuery = true)
    List<Prescripcion> findByPrescripcionId(@Param("prescripcionId") Long prescripcionId);

    @Query("SELECT p FROM Prescripcion p WHERE LOWER(p.paciente.nombre) LIKE LOWER(CONCAT('%', :nombrePaciente, '%'))")
    List<Prescripcion> findByPacienteNombreContainingIgnoreCase(@Param("nombrePaciente") String nombrePaciente);

    @Query("SELECT p FROM Prescripcion p WHERE LOWER(p.medico.nombre) LIKE LOWER(CONCAT('%', :nombreMedico, '%'))")
    List<Prescripcion> findByMedicoNombreContainingIgnoreCase(@Param("nombreMedico") String nombreMedico);

}