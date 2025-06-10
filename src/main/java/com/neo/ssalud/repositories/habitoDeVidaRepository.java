package com.neo.ssalud.repositories;

import com.neo.ssalud.models.habitoDeVida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface habitoDeVidaRepository extends JpaRepository<habitoDeVida, Long> {

    Optional<habitoDeVida> findByPacienteIdAndTipo(Long pacienteId, String tipo);

    List<habitoDeVida> findByPacienteId(Long id);
}
