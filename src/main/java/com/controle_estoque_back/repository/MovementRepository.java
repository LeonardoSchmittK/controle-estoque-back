package com.controle_estoque_back.repository;

import com.controle_estoque_back.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {}

