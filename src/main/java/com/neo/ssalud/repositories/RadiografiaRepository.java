package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Radiografia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RadiografiaRepository extends JpaRepository<Radiografia, Long> {
    List<Radiografia> findByPacienteId(Long pacienteId);
}