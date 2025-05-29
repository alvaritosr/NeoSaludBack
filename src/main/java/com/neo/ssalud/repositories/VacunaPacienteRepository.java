package com.neo.ssalud.repositories;

import com.neo.ssalud.models.VacunaPaciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacunaPacienteRepository extends JpaRepository<VacunaPaciente, Long> {
    List<VacunaPaciente> findByPacienteId(Long pacienteId);
    @Transactional
    @Modifying
    @Query("DELETE FROM VacunaPaciente vp WHERE vp.vacuna.id = :vacunaId")
    void deleteByVacunaId(@Param("vacunaId") Long vacunaId);
}
