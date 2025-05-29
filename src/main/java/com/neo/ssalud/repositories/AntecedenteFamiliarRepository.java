package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Alergia;
import com.neo.ssalud.models.AntecedenteFamiliar;
import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AntecedenteFamiliarRepository extends JpaRepository<AntecedenteFamiliar, Long> {
    List<AntecedenteFamiliar> findByPaciente(Paciente paciente);

    Optional<AntecedenteFamiliar> findByEnfermedadLikeIgnoreCase(String nombre);
}
