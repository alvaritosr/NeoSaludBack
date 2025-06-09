package com.neo.ssalud.controllers;

import com.neo.ssalud.models.habitoDeVida;
import com.neo.ssalud.services.HabitoDeVidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habito-de-vida")
@AllArgsConstructor
public class HabitoDeVidaController {

    private final HabitoDeVidaService habitoDeVidaService;

    @PostMapping("/{nhPaciente}")
    public ResponseEntity<habitoDeVida> crearHabitoDeVida(@PathVariable String nhPaciente,
                                                          @RequestBody habitoDeVida habito,
                                                          @RequestParam String usernameMedico) {
        habitoDeVida nuevoHabito = habitoDeVidaService.crearHabitoDeVida(nhPaciente, habito, usernameMedico);
        return new ResponseEntity<>(nuevoHabito, HttpStatus.CREATED);
    }

    @GetMapping("/{nhPaciente}/tipos")
    public ResponseEntity<List<String>> obtenerTiposDeHabitosDeVida(@PathVariable String nhPaciente,
                                                                    @RequestParam String usernameMedico) {
        List<String> tipos = habitoDeVidaService.obtenerTiposDeHabitosDeVida(nhPaciente, usernameMedico);
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{nhPaciente}/habitos")
    public ResponseEntity<habitoDeVida> obtenerHabitoDeVidaPorTipo(@PathVariable String nhPaciente,
                                                                   @RequestParam String tipo,
                                                                   @RequestParam String usernameMedico) {
        habitoDeVida habito = habitoDeVidaService.obtenerHabitoDeVidaPorTipo(nhPaciente, tipo, usernameMedico);
        return ResponseEntity.ok(habito);
    }
}