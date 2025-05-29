package com.neo.ssalud.controllers;

import com.neo.ssalud.models.habitoDeVida;
import com.neo.ssalud.services.HabitoDeVidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habito-de-vida")
@AllArgsConstructor
public class HabitoDeVidaController {

    private final HabitoDeVidaService habitoDeVidaService;

    @PostMapping
    public ResponseEntity<habitoDeVida> crearHabitoDeVida(
            @RequestParam String nh,
            @RequestParam String tipo,
            @RequestParam String frecuencia,
            @RequestParam String duracion,
            @RequestParam String observaciones) {
        habitoDeVida habito = habitoDeVidaService.crearHabitoDeVida(nh, tipo, frecuencia, duracion, observaciones);
        return ResponseEntity.ok(habito);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> obtenerTiposDeHabitosDeVida(@RequestParam String nh) {
        List<String> tipos = habitoDeVidaService.obtenerTiposDeHabitosDeVida(nh);
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{habitoId}")
    public ResponseEntity<habitoDeVida> obtenerHabitoDeVidaPorId(
            @RequestParam String nh,
            @PathVariable Long habitoId) {
        habitoDeVida habito = habitoDeVidaService.obtenerHabitoDeVidaPorId(nh, habitoId);
        return ResponseEntity.ok(habito);
    }
}