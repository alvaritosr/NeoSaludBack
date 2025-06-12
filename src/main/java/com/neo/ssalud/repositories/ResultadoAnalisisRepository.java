package com.neo.ssalud.repositories;

import com.neo.ssalud.models.ResultadoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoAnalisisRepository extends JpaRepository<ResultadoAnalisis, Long> {

    List<ResultadoAnalisis> findByAnalisisMedicoId(Long analisisMedicoId);
}
