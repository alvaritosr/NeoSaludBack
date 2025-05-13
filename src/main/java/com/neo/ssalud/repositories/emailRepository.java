package com.neo.ssalud.repositories;

import com.neo.ssalud.models.email;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface emailRepository extends JpaRepository<email, Long> {
}
