package com.neo.ssalud.repositories;

import com.neo.ssalud.models.AntecedenteFamiliar;
import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AntecedenteFamiliarRepository extends JpaRepository<AntecedenteFamiliar, Long> {
    List<AntecedenteFamiliar> findByPaciente(Paciente paciente);
}
