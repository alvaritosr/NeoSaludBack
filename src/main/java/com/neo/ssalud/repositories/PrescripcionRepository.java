package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescripcionRepository extends JpaRepository<Prescripcion, Long> {
    @Query(value = "SELECT * FROM neosalud.prescripcion WHERE id = :prescripcionId", nativeQuery = true)
    List<Prescripcion> findByPrescripcionId(@Param("prescripcionId") Long prescripcionId);
}