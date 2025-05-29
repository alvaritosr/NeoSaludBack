package com.neo.ssalud.services;

import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Prescripcion;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.repositories.PrescripcionRepository;
import com.neo.ssalud.dto.PrescripcionDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrescripcionService {
    private final PrescripcionRepository prescripcionRepository;
    private final medicoRepository medicoRepository;
    private final pacienteRepository pacienteRepository;

    public PrescripcionService(PrescripcionRepository prescripcionRepository, medicoRepository medicoRepository, pacienteRepository pacienteRepository) {
        this.prescripcionRepository = prescripcionRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public PrescripcionDTO convertirAPrescripcionDTO(Prescripcion prescripcion) {
        return new PrescripcionDTO(
                prescripcion.getId(),
                prescripcion.getNombreComercial(),
                prescripcion.getNombreGenerico(),
                prescripcion.getMedico() != null ? prescripcion.getMedico().getNombre() : null,
                prescripcion.getPaciente() != null ? prescripcion.getPaciente().getNombre() : null,
                prescripcion.getFechaEmision(),
                prescripcion.getDosis(),
                prescripcion.getFrecuencia(),
                prescripcion.getDuracionDias(),
                prescripcion.getNotasAdicionales()
        );
    }


    public PrescripcionDTO obtenerPorId(Long id) {
        Prescripcion prescripcion = prescripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescripción no encontrada con ID: " + id));
        return new PrescripcionDTO(
                prescripcion.getId(),
                prescripcion.getNombreComercial(),
                prescripcion.getNombreGenerico(),
                prescripcion.getMedico() != null ? prescripcion.getMedico().getNombre() : null,
                prescripcion.getPaciente() != null ? prescripcion.getPaciente().getNombre() : null,
                prescripcion.getFechaEmision(),
                prescripcion.getDosis(),
                prescripcion.getFrecuencia(),
                prescripcion.getDuracionDias(),
                prescripcion.getNotasAdicionales()
        );
    }

    public Prescripcion guardar(Prescripcion prescripcion) {
        return prescripcionRepository.save(prescripcion);
    }

    public List<Prescripcion> obtenerTodas() {
        return prescripcionRepository.findAll();
    }

    public List<Prescripcion> obtenerPorPrescripcionId(Long id) {
        return prescripcionRepository.findByPrescripcionId(id);
    }

    public List<Prescripcion> buscarPorPaciente(String nombrePaciente) {
        return prescripcionRepository.findByPacienteNombreContainingIgnoreCase(nombrePaciente);
    }

    public List<Prescripcion> obtenerPorMedico(String nombrePrescriptor) {
        return prescripcionRepository.findByMedicoNombreContainingIgnoreCase(nombrePrescriptor);
    }

    public Prescripcion crearPrescripcion(Prescripcion prescripcion, String username) {
        // Validar que el médico exista
        Medico medico = medicoRepository.findTopByUsername(username)
                .orElseThrow(() -> new RuntimeException("El médico con username '" + username + "' no fue encontrado."));

        // Validar que el paciente exista
        Paciente paciente = pacienteRepository.findById(prescripcion.getPaciente().getId())
                .orElseThrow(() -> new RuntimeException("El paciente con ID '" + prescripcion.getPaciente().getId() + "' no fue encontrado."));

        // Asignar médico y paciente a la prescripción
        prescripcion.setMedico(medico);
        prescripcion.setPaciente(paciente);

        // Establecer la fecha de emisión
        prescripcion.setFechaEmision(LocalDate.now());

        // Guardar la prescripción
        return prescripcionRepository.save(prescripcion);
    }
}