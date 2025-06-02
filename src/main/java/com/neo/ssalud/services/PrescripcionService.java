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
import java.util.NoSuchElementException;

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
                prescripcion.getNombrePrescriptor(),
                prescripcion.getNumeroColegiado(),
                prescripcion.getEspecialidad(),
                prescripcion.getFirmaDigital(),
                prescripcion.getContactoPrescriptor(),
                prescripcion.getNombreGenerico(),
                prescripcion.getNombreComercial(),
                prescripcion.getConcentracion(),
                prescripcion.getFormaFarmaceutica(),
                prescripcion.getViaAdministracion(),
                prescripcion.getDosis(),
                prescripcion.getFrecuencia(),
                prescripcion.getDuracionDias(),
                prescripcion.getCantidadEnvases(),
                prescripcion.getLugarEmision(),
                prescripcion.getFechaEmision(),
                prescripcion.getIndicacionesEspecificas(),
                prescripcion.getNotasAdicionales(),
                prescripcion.getMedico() != null ? prescripcion.getMedico().getId() : null,
                prescripcion.getPaciente() != null ? prescripcion.getPaciente().getId() : null
        );
    }

    public PrescripcionDTO obtenerPorId(Long id) {
        Prescripcion prescripcion = prescripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescripción no encontrada con ID: " + id));
        return convertirAPrescripcionDTO(prescripcion);
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

    public Paciente obtenerPacientePorNh(String nhPaciente) {
        return (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));
    }

    public Medico obtenerMedicoPorNh(String nhPaciente) {
        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        return paciente.getMedico();
    }

    public Prescripcion crearPrescripcion(String nhPaciente, Prescripcion prescripcion, String usernameMedico) {
        Paciente paciente = obtenerPacientePorNh(nhPaciente);
        if (paciente == null) {
            throw new NoSuchElementException("Paciente no encontrado con nh: " + nhPaciente);
        }

        Medico medico = paciente.getMedico();
        if (medico == null) {
            throw new NoSuchElementException("Médico no encontrado para el paciente con nh: " + nhPaciente);
        }

        prescripcion.setMedico(medico);
        prescripcion.setPaciente(paciente);

        return prescripcionRepository.save(prescripcion);
    }
}