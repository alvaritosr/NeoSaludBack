package com.neo.ssalud.services;

import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Vacuna;
import com.neo.ssalud.models.VacunaPaciente;
import com.neo.ssalud.repositories.VacunaPacienteRepository;
import com.neo.ssalud.repositories.VacunaRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VacunaPacienteService {

    @Autowired
    private VacunaPacienteRepository vacunaPacienteRepository;

    @Autowired
    private pacienteRepository pacienteRepository;

    @Autowired
    private VacunaRepository vacunaRepository;

    public VacunaPaciente asignarVacuna(Long pacienteId, Long vacunaId, String dosis, String fecha) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(pacienteId);
        Optional<Vacuna> vacunaOpt = vacunaRepository.findById(vacunaId);

        if (pacienteOpt.isEmpty() || vacunaOpt.isEmpty()) {
            throw new RuntimeException("Paciente o Vacuna no encontrada");
        }

        VacunaPaciente vacunaPaciente = new VacunaPaciente();
        vacunaPaciente.setPaciente(pacienteOpt.get());
        vacunaPaciente.setVacuna(vacunaOpt.get());
        vacunaPaciente.setDosis(dosis);
        vacunaPaciente.setFechaAdministracion(LocalDateTime.parse(fecha));

        return vacunaPacienteRepository.save(vacunaPaciente);
    }

    public List<VacunaPaciente> obtenerVacunasDePaciente(Long pacienteId) {
        return vacunaPacienteRepository.findByPacienteId(pacienteId);
    }

    public void eliminarVacunaDePaciente(Long vacunaPacienteId) {
        VacunaPaciente vacunaPaciente = vacunaPacienteRepository.findById(vacunaPacienteId)
                .orElseThrow(() -> new EntityNotFoundException("VacunaPaciente con ID " + vacunaPacienteId + " no encontrada."));

        vacunaPacienteRepository.delete(vacunaPaciente);
    }


}
