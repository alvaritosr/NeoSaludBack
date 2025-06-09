package com.neo.ssalud.services;

import com.neo.ssalud.models.habitoDeVida;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.habitoDeVidaRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HabitoDeVidaService {

    private final habitoDeVidaRepository habitoDeVidaRepository;
    private final pacienteRepository pacienteRepository;
    private final medicoRepository medicoRepository;

    public HabitoDeVidaService(habitoDeVidaRepository habitoDeVidaRepository, pacienteRepository pacienteRepository, medicoRepository medicoRepository) {
        this.habitoDeVidaRepository = habitoDeVidaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public habitoDeVida crearHabitoDeVida(String nh, habitoDeVida habito, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        habito.setPaciente(paciente);
        return habitoDeVidaRepository.save(habito);
    }

    public List<String> obtenerTiposDeHabitosDeVida(String nh, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        List<habitoDeVida> habitos = habitoDeVidaRepository.findByPacienteId(paciente.getId());
        List<String> tipos = new ArrayList<>();
        for (habitoDeVida habito : habitos) {
            tipos.add(habito.getTipo());
        }

        return tipos;
    }

    public habitoDeVida obtenerHabitoDeVidaPorTipo(String nh, String tipo, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return habitoDeVidaRepository.findByPacienteIdAndTipo(paciente.getId(), tipo)
                .orElseThrow(() -> new NoSuchElementException("Hábito de vida no encontrado con el tipo: " + tipo));
    }
}