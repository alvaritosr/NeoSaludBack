package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Vacuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacunaRepository extends JpaRepository<Vacuna, Long> {

}
