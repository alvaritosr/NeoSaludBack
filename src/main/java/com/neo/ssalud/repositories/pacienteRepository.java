package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pacienteRepository extends JpaRepository<Paciente, Long> {
}
