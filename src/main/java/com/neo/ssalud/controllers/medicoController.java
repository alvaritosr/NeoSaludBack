package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.PacienteDTO;
import com.neo.ssalud.models.Consulta;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.services.medicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/medicos")
@AllArgsConstructor
public class medicoController {

    private final medicoService medicoService;

    @GetMapping("/all")
    public ResponseEntity<List<Medico>> obtenerTodosLosMedicos() {
        List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
        return ResponseEntity.ok(medicos);
    }

    @PostMapping("/{username}/pacientes")
    public ResponseEntity<Paciente> crearPaciente(@PathVariable String username, @RequestBody PacienteDTO pacienteDTO) {
        Paciente nuevoPaciente = medicoService.crearPaciente(pacienteDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPaciente);
    }

    @PutMapping("/pacientes/{nh}/cambiar-medico")
    public ResponseEntity<Paciente> cambiarMedicoDePaciente(
            @PathVariable String nh,
            @RequestParam String nuevoUsernameMedico) {
        Paciente pacienteActualizado = medicoService.cambiarMedicoDePaciente(nh, nuevoUsernameMedico);
        return ResponseEntity.ok(pacienteActualizado);
    }

    @GetMapping("/pacientes/buscar")
    public ResponseEntity<List<Paciente>> buscarPacientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String pasaporte,
            @RequestParam(required = false) String nuss,
            @RequestParam(required = false) String nh,
            @RequestParam(required = false) String nuhsa,
            @RequestParam(required = false) String anyoNacimiento,
            @RequestParam(required = false) String direccion) {
        List<Paciente> pacientes = medicoService.buscarPacientes(
                nombre, primerApellido, segundoApellido, dni, pasaporte, nuss, nh, nuhsa, anyoNacimiento, direccion);
        return ResponseEntity.ok(pacientes);
    }

    @PostMapping("/pacientes/{nh}/consultas")
    public ResponseEntity<Consulta> crearConsulta(
            @PathVariable String nh,
            @RequestBody Consulta consulta) {
        Consulta nuevaConsulta = medicoService.crearConsulta(nh, consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaConsulta);
    }

    @GetMapping("/{username}/consultas")
    public ResponseEntity<List<Consulta>> verConsultasPorMedico(@PathVariable String username) {
        List<Consulta> consultas = medicoService.verConsultasPorMedico(username);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/pacientes/{nh}/consultas")
    public ResponseEntity<List<LocalDateTime>> verConsulta(
            @PathVariable String nh,
            @RequestParam String usernameMedico) {
        List<LocalDateTime> fechasConsultas = medicoService.verConsulta(nh, usernameMedico)
                .stream()
                .map(Consulta::getFechaConsulta)
                .toList();
        return ResponseEntity.ok(fechasConsultas);
    }

    @GetMapping("/pacientes/{nh}/consultas/{idConsulta}")
    public ResponseEntity<Consulta> verDetalleConsulta(
            @PathVariable String nh,
            @PathVariable Long idConsulta,
            @RequestParam String usernameMedico) {
        Consulta consulta = medicoService.verDetalleConsulta(nh, idConsulta, usernameMedico);
        return ResponseEntity.ok(consulta);
    }

    @GetMapping("/pacientes/{nh}")
    public ResponseEntity<Paciente> verDetallePaciente(
            @PathVariable String nh,
            @RequestParam String usernameMedico) {
        Paciente paciente = medicoService.verDetallePaciente(nh, usernameMedico);
        return ResponseEntity.ok(paciente);
    }

    @GetMapping("/pacientes/urgencias/{nh}")
    public ResponseEntity<Paciente> verDetallePacienteSinMedico(
            @PathVariable String nh) {
        Paciente paciente = medicoService.verDetallePacienteSinMedico(nh);
        return ResponseEntity.ok(paciente);
    }
}