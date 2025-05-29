package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Tac;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface tacRepository extends JpaRepository<Tac, Long> {
    List<Tac> findByPacienteId(Long pacienteId);
}
