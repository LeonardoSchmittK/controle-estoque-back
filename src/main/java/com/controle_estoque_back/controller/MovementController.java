package com.controle_estoque_back.controller;

import com.controle_estoque_back.dto.MovementRequestDTO;
import com.controle_estoque_back.dto.MovementResponseDTO;
import com.controle_estoque_back.service.MovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService service;

    public MovementController(MovementService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<MovementResponseDTO> create(@Valid @RequestBody MovementRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }
}
