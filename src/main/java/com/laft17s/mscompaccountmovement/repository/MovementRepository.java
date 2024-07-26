package com.laft17s.mscompaccountmovement.repository;

import com.laft17s.mscompaccountmovement.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
}
