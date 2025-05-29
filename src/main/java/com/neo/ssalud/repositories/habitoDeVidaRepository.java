package com.neo.ssalud.repositories;

import com.neo.ssalud.models.habitoDeVida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface habitoDeVidaRepository extends JpaRepository<habitoDeVida, Long> {

    List<habitoDeVida> findByPacienteId(Long id);
}
