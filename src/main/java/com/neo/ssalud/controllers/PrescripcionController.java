package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.PrescripcionDTO;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Prescripcion;
import com.neo.ssalud.services.PrescripcionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import com.neo.ssalud.dto.PrescripcionDTO;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/prescripciones")
@CrossOrigin(origins = "*")
public class PrescripcionController {
    private final PrescripcionService service;

    public PrescripcionController(PrescripcionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Prescripcion> prescribir(@RequestBody Prescripcion prescripcion) {
        return ResponseEntity.ok(service.guardar(prescripcion));
    }


    @GetMapping("/{prescripcionId}")
    public ResponseEntity<PrescripcionDTO> obtenerPorId(@PathVariable Long prescripcionId) {
        PrescripcionDTO prescripcionDTO = service.obtenerPorId(prescripcionId);
        return ResponseEntity.ok(prescripcionDTO);
    }

    @GetMapping
    public ResponseEntity<List<Prescripcion>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/paciente/{nombre}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'PACIENTE')")
    public ResponseEntity<?> obtenerPorPaciente(@PathVariable String nombre) {
        try {
            String nombreDecodificado = nombre.contains("%") ?
                    URLDecoder.decode(nombre, StandardCharsets.UTF_8) :
                    nombre;

            List<Prescripcion> prescripciones = service.buscarPorPaciente(nombreDecodificado);

            if (prescripciones.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron prescripciones para el paciente: " + nombreDecodificado);
            }

            return ResponseEntity.ok(prescripciones);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar prescripciones: " + e.getMessage());
        }
    }

    @GetMapping("/medico/{nombrePrescriptor}")
    public ResponseEntity<List<Prescripcion>> obtenerPorMedico(@PathVariable String nombrePrescriptor) {
        return ResponseEntity.ok(service.obtenerPorMedico(nombrePrescriptor));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPrescripcion(@RequestParam String nhPaciente, @Valid @RequestBody Prescripcion prescripcion) {
        System.out.println("nhPaciente: " + nhPaciente);
        System.out.println("Prescripción: " + prescripcion);

        try {
            Paciente paciente = service.obtenerPacientePorNh(nhPaciente);
            if (paciente == null) {
                return ResponseEntity.badRequest().body("No se encontró el paciente con nhPaciente: " + nhPaciente);
            }

            Medico medico = paciente.getMedico();
            if (medico == null) {
                return ResponseEntity.badRequest().body("No se encontró un médico asociado al paciente con nhPaciente: " + nhPaciente);
            }

            prescripcion.setPaciente(paciente);
            prescripcion.setMedico(medico);

            Prescripcion nuevaPrescripcion = service.crearPrescripcion(nhPaciente, prescripcion, medico.getUsername());
            PrescripcionDTO prescripcionDTO = service.convertirAPrescripcionDTO(nuevaPrescripcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(prescripcionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/eliminar/{prescripcionId}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Long prescripcionId) {
        try {
            service.eliminarPorId(prescripcionId);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }
}