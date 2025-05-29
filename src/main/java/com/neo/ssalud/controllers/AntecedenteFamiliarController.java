package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Alergia;
import com.neo.ssalud.models.AntecedenteFamiliar;
import com.neo.ssalud.services.AntecedenteFamiliarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/antecedentes-familiares")
@AllArgsConstructor
public class AntecedenteFamiliarController {

    private final AntecedenteFamiliarService antecedenteFamiliarService;

    @PostMapping("/pacientes/{nh}")
    public ResponseEntity<AntecedenteFamiliar> crearAntecedenteFamiliar(
            @PathVariable String nh,
            @RequestParam String usernameMedico,
            @RequestBody AntecedenteFamiliar antecedenteFamiliar) {
        AntecedenteFamiliar nuevoAntecedente = antecedenteFamiliarService.crearAntecedenteFamiliar(nh, usernameMedico, antecedenteFamiliar);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAntecedente);
    }

    @GetMapping("/pacientes/{nh}")
    public ResponseEntity<List<String>> verAntecedentesFamiliares(
            @PathVariable String nh,
            @RequestParam String usernameMedico) {
        List<String> enfermedades = antecedenteFamiliarService.verAntecedentesFamiliares(nh, usernameMedico);
        return ResponseEntity.ok(enfermedades);
    }

    @GetMapping("/pacientes/{nh}/detalles/{idAntecedente}")
    public ResponseEntity<AntecedenteFamiliar> verAntecedentesFamiliaresDetalles(
            @PathVariable String nh,
            @PathVariable Long idAntecedente,
            @RequestParam String usernameMedico) {
        AntecedenteFamiliar antecedenteFamiliar = antecedenteFamiliarService.verAntecedenteFamiliarDetalle(nh, idAntecedente, usernameMedico);
        return ResponseEntity.ok(antecedenteFamiliar);
    }

    @GetMapping("/pacientes/{nhPaciente}/detalles")
    public ResponseEntity<AntecedenteFamiliar> obtenerAntecedenteFamiliarPorNombre(@PathVariable String nhPaciente,
                                                           @RequestParam String nombre,
                                                           @RequestParam String usernameMedico) {
        AntecedenteFamiliar antecedenteFamiliar = antecedenteFamiliarService.obtenerAntecedenteFamiliarPorNombre(nhPaciente, nombre, usernameMedico);
        return ResponseEntity.ok(antecedenteFamiliar);
    }

}