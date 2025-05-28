package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Alergia;
import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlergiaRepository extends JpaRepository<Alergia, Long> {
    List<Alergia> findByPaciente(Paciente paciente);
}
