package com.neo.ssalud.repositories;

import com.neo.ssalud.models.AnalisisMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalisisMedicoRepository extends JpaRepository<AnalisisMedico, Long> {
    List<AnalisisMedico> findByPacienteId(Long id);
}
