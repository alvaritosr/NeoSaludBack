package com.neo.ssalud.services;

import com.neo.ssalud.models.Prescripcion;
import com.neo.ssalud.repositories.PrescripcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescripcionService {
    private final PrescripcionRepository repository;

    public PrescripcionService(PrescripcionRepository repository) {
        this.repository = repository;
    }

    public Prescripcion guardar(Prescripcion prescripcion) {
        return repository.save(prescripcion);
    }

    public List<Prescripcion> obtenerPorPrescripcionId(Long prescripcionId) {
        return repository.findByPrescripcionId(prescripcionId);
    }

    public List<Prescripcion> obtenerTodas() {
        return repository.findAll();
    }
}