package com.neo.ssalud.services;

import com.neo.ssalud.models.Ingresos;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.IngresosRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IngresosService {

    @Autowired
    private IngresosRepository ingresosRepository;

    @Autowired
    private pacienteRepository pacienteRepository;

    @Autowired
    private medicoRepository medicoRepository;

    public Ingresos crearIngreso(String nh, String username, Ingresos ingreso) {
        Optional<Medico> medico = medicoRepository.findByUsername(username);
        if (medico.isEmpty()) {
            throw new IllegalArgumentException("El médico no está autenticado o no existe.");
        }

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        ingreso.setPaciente(paciente);
        return ingresosRepository.save(ingreso);
    }

    public List<Ingresos> verIngresos(String username) {
        Optional<Medico> medico = medicoRepository.findByUsername(username);
        if (medico.isEmpty()) {
            throw new IllegalArgumentException("El médico no está autenticado o no existe.");
        }
        return ingresosRepository.findAll();
    }
}