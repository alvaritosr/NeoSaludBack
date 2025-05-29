package com.neo.ssalud.repositories;

import com.neo.ssalud.models.AnalisisMedico;
import com.neo.ssalud.models.AntecedenteFamiliar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnalisisMedicoRepository extends JpaRepository<AnalisisMedico, Long> {
    List<AnalisisMedico> findByPacienteId(Long id);

    Optional<AnalisisMedico> findByTipoLikeIgnoreCase(String nombre);

}
