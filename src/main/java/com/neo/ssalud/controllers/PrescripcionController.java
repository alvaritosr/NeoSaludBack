package com.neo.ssalud.controllers;


import com.neo.ssalud.models.Prescripcion;
import com.neo.ssalud.services.PrescripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescripciones")
public class PrescripcionController {
    private final PrescripcionService service;

    public PrescripcionController(PrescripcionService service) {
        this.service = service;
    }

    @PostMapping
    public Prescripcion prescribir(@RequestBody Prescripcion prescripcion) {
        return service.guardar(prescripcion);
    }

    @GetMapping("/{prescripcionId}")
    public ResponseEntity<List<Prescripcion>> obtenerPorPrescripcionId(@PathVariable Long prescripcionId) {
        List<Prescripcion> prescripciones = service.obtenerPorPrescripcionId(prescripcionId);
        return ResponseEntity.ok(prescripciones);
    }

    @GetMapping("/allPacientes")
    public ResponseEntity<List<Prescripcion>> obtenerTodas() {
        List<Prescripcion> prescripciones = service.obtenerTodas();
        return ResponseEntity.ok(prescripciones);
    }
}

