package com.neo.ssalud.services;

import com.neo.ssalud.models.AnalisisMedico;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.AnalisisMedicoRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class AnalisisMedicoService {

    private final AnalisisMedicoRepository analisisMedicoRepository;
    private final pacienteRepository pacienteRepository;
    private final medicoRepository medicoRepository;

    public AnalisisMedicoService(AnalisisMedicoRepository analisisMedicoRepository, pacienteRepository pacienteRepository, medicoRepository medicoRepository) {
        this.analisisMedicoRepository = analisisMedicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public AnalisisMedico crearAnalisisMedico(String nh, String usernameMedico, String tipo) {

        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        AnalisisMedico analisisMedico = new AnalisisMedico();
        analisisMedico.setPaciente(paciente);
        analisisMedico.setTipo(tipo);
        analisisMedico.setFecha(LocalDateTime.now());

        return analisisMedicoRepository.save(analisisMedico);
    }

    public List<String> verTiposAnalisisMedicos(String nh) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameMedico = authentication.getName();

        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        List<AnalisisMedico> analisisMedicos = analisisMedicoRepository.findByPacienteId(paciente.getId());
        List<String> tiposAnalisis = new ArrayList<>();
        for (AnalisisMedico analisis : analisisMedicos) {
            tiposAnalisis.add(analisis.getTipo());
        }

        return tiposAnalisis;
    }

    public AnalisisMedico verAnalisisMedicoPorNh(String nh, Long analisisId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usernameMedico = authentication.getName();

        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return analisisMedicoRepository.findById(analisisId)
                .orElseThrow(() -> new NoSuchElementException("Análisis médico no encontrado con el ID: " + analisisId));
    }


}