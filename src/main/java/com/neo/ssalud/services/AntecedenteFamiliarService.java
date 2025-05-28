package com.neo.ssalud.services;

import com.neo.ssalud.models.AntecedenteFamiliar;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.AntecedenteFamiliarRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AntecedenteFamiliarService {

    private final pacienteRepository pacienteRepository;
    private final medicoRepository medicoRepository;
    private final AntecedenteFamiliarRepository antecedenteFamiliarRepository;

    public AntecedenteFamiliar crearAntecedenteFamiliar(String nh, String usernameMedico, AntecedenteFamiliar antecedenteFamiliar) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        antecedenteFamiliar.setPaciente(paciente);
        return antecedenteFamiliarRepository.save(antecedenteFamiliar);
    }

    public List<String> verAntecedentesFamiliares(String nh, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return antecedenteFamiliarRepository.findByPaciente(paciente)
                .stream()
                .map(AntecedenteFamiliar::getEnfermedad)
                .toList();
    }

    public AntecedenteFamiliar verAntecedenteFamiliarDetalle(String nh, Long idAntecedente, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return antecedenteFamiliarRepository.findById(idAntecedente)
                .orElseThrow(() -> new NoSuchElementException("Antecedente familiar no encontrado con el id: " + idAntecedente));
    }
}