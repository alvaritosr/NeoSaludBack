package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface medicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findTopByUsername(String username);
    Optional<Medico> findByEmail(String email);
}
