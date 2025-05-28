package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Consulta;
import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPaciente(Paciente paciente);
}