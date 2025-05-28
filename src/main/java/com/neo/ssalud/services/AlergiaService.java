package com.neo.ssalud.services;

import com.neo.ssalud.models.Alergia;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.AlergiaRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlergiaService {

    private final AlergiaRepository alergiaRepository;
    private final pacienteRepository pacienteRepository;
    private final medicoRepository medicoRepository;

    public AlergiaService(AlergiaRepository alergiaRepository, pacienteRepository pacienteRepository, medicoRepository medicoRepository) {
        this.alergiaRepository = alergiaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Alergia crearAlergia(String nhPaciente, Alergia alergia, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        alergia.setPaciente(paciente);

        return alergiaRepository.save(alergia);
    }

    public List<String> obtenerSustanciasPorPaciente(String nhPaciente, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return alergiaRepository.findByPaciente(paciente)
                .stream()
                .map(Alergia::getSustancia)
                .toList();
    }

    public Alergia obtenerAlergiaPorId(String nhPaciente, Long alergiaId, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return alergiaRepository.findById(alergiaId)
                .filter(alergia -> alergia.getPaciente().getId().equals(paciente.getId()))
                .orElseThrow(() -> new NoSuchElementException("Alergia no encontrada para el paciente especificado."));
    }
}