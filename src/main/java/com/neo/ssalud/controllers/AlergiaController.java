package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Alergia;
import com.neo.ssalud.services.AlergiaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alergias")
public class AlergiaController {

    private final AlergiaService alergiaService;

    public AlergiaController(AlergiaService alergiaService) {
        this.alergiaService = alergiaService;
    }

    @PostMapping("/{nhPaciente}")
    public ResponseEntity<Alergia> crearAlergia(@PathVariable String nhPaciente,
                                                @RequestBody Alergia alergia,
                                                @RequestParam String usernameMedico) {
        Alergia nuevaAlergia = alergiaService.crearAlergia(nhPaciente, alergia, usernameMedico);
        return new ResponseEntity<>(nuevaAlergia, HttpStatus.CREATED);
    }

    @GetMapping("/{nhPaciente}/sustancias")
    public ResponseEntity<List<String>> obtenerSustanciasPorPaciente(@PathVariable String nhPaciente,
                                                                     @RequestParam String usernameMedico) {
        List<String> sustancias = alergiaService.obtenerSustanciasPorPaciente(nhPaciente, usernameMedico);
        return ResponseEntity.ok(sustancias);
    }

    @GetMapping("/{nhPaciente}/alergias/{alergiaId}")
    public ResponseEntity<Alergia> obtenerAlergiaPorId(@PathVariable String nhPaciente,
                                                       @PathVariable Long alergiaId,
                                                       @RequestParam String usernameMedico) {
        Alergia alergia = alergiaService.obtenerAlergiaPorId(nhPaciente, alergiaId, usernameMedico);
        return ResponseEntity.ok(alergia);
    }

}