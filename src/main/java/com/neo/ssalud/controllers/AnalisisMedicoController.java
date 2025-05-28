package com.neo.ssalud.controllers;

import com.neo.ssalud.models.AnalisisMedico;
import com.neo.ssalud.services.AnalisisMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analisis-medico")
@AllArgsConstructor
public class AnalisisMedicoController {

    private final AnalisisMedicoService analisisMedicoService;

    @PostMapping
    public ResponseEntity<AnalisisMedico> crearAnalisisMedico(
            @RequestParam String nh,
            @RequestParam String usernameMedico,
            @RequestParam String tipo) {
        AnalisisMedico analisisMedico = analisisMedicoService.crearAnalisisMedico(nh, usernameMedico, tipo);
        return ResponseEntity.ok(analisisMedico);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<String>> verTiposAnalisisMedicos(@RequestParam String nh) {
        List<String> tiposAnalisis = analisisMedicoService.verTiposAnalisisMedicos(nh);
        return ResponseEntity.ok(tiposAnalisis);
    }

    @GetMapping("/{analisisId}")
    public ResponseEntity<AnalisisMedico> verAnalisisMedico(
            @RequestParam String nh,
            @PathVariable Long analisisId) {
        AnalisisMedico analisisMedico = analisisMedicoService.verAnalisisMedicoPorNh(nh, analisisId);
        return ResponseEntity.ok(analisisMedico);
    }

}