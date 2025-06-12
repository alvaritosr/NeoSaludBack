package com.neo.ssalud.controllers;

import com.neo.ssalud.models.ResultadoAnalisis;
import com.neo.ssalud.services.ResultadoAnalisisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultados-analisis")
@RequiredArgsConstructor
public class ResultadoAnalisisController {

    private final ResultadoAnalisisService resultadoAnalisisService;

    @PostMapping("/crear")
    public ResponseEntity<ResultadoAnalisis> crearResultadoAnalisis(
            @RequestParam Long analisisMedicoId,
            @RequestParam String parametro,
            @RequestParam Double valor,
            @RequestParam String unidad,
            @RequestParam Double valorReferencia) {
        ResultadoAnalisis resultadoAnalisis = resultadoAnalisisService.crearResultadoAnalisis(
                analisisMedicoId, parametro, valor, unidad, valorReferencia);
        return ResponseEntity.ok(resultadoAnalisis);
    }

    @GetMapping("/ver/{analisisMedicoId}")
    public ResponseEntity<List<ResultadoAnalisis>> verResultadosPorAnalisisMedico(@PathVariable Long analisisMedicoId) {
        List<ResultadoAnalisis> resultados = resultadoAnalisisService.verResultadosPorAnalisisMedico(analisisMedicoId);
        return ResponseEntity.ok(resultados);
    }
}