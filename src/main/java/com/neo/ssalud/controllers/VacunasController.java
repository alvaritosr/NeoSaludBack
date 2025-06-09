package com.neo.ssalud.controllers;


import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Vacuna;
import com.neo.ssalud.models.VacunaPaciente;
import com.neo.ssalud.repositories.VacunaPacienteRepository;
import com.neo.ssalud.repositories.VacunaRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.services.VacunaPacienteService;
import com.neo.ssalud.services.VacunaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vacunas")
@RequiredArgsConstructor
public class VacunasController {

    @Autowired
    private VacunaService vacunaService;

    @Autowired
    private VacunaRepository vacunaRepository;

    @Autowired
    private VacunaPacienteRepository vacunaPacienteRepository;

    @Autowired
    private VacunaPacienteService vacunaPacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Vacuna> obtenerVacunaPorId(@PathVariable Long id) {
        return vacunaService.obtenerVacunaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Vacuna>> obtenerTodasLasVacunas() {
        List<Vacuna> vacunas = vacunaService.obtenerTodasLasVacunas();
        return ResponseEntity.ok(vacunas);
    }

    @PostMapping
        public ResponseEntity<?> crearVacuna(@RequestBody Vacuna vacuna) {
        return ResponseEntity.ok(vacunaService.crearVacuna(vacuna));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVacuna(@PathVariable Long id, @RequestBody Vacuna vacunaActualizada) {
        return ResponseEntity.ok(vacunaService.actualizarVacuna(id, vacunaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVacuna(@PathVariable Long id) {
        Optional<Vacuna> vacunaOpt = vacunaRepository.findById(id);
        if (vacunaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vacuna con ID " + id + " no encontrada.");
        }

        vacunaPacienteRepository.deleteByVacunaId(id);
        vacunaRepository.delete(vacunaOpt.get());

        return ResponseEntity.ok("Vacuna eliminada correctamente.");
    }



    @PostMapping("/asignar")
    public ResponseEntity<?> asignarVacuna(
            @RequestParam Long pacienteId,
            @RequestParam Long vacunaId,
            @RequestParam String dosis,
            @RequestParam String fecha) {
        return ResponseEntity.ok(vacunaPacienteService.asignarVacuna(pacienteId, vacunaId, dosis, fecha));
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<?> obtenerVacunasDePaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(vacunaPacienteService.obtenerVacunasDePaciente(pacienteId));
    }

    @DeleteMapping("/quitarVacuna/{vacunaPacienteId}")
    public ResponseEntity<?> eliminarVacunaDePaciente(@PathVariable Long vacunaPacienteId) {
        try {
            vacunaPacienteService.eliminarVacunaDePaciente(vacunaPacienteId);
            return ResponseEntity.ok("Vacuna eliminada del paciente correctamente.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
