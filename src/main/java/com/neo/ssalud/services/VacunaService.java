package com.neo.ssalud.services;

import com.neo.ssalud.models.Vacuna;
import com.neo.ssalud.repositories.VacunaPacienteRepository;
import com.neo.ssalud.repositories.VacunaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacunaService {

    @Autowired
    private VacunaRepository vacunaRepository;

    @Autowired
    private VacunaPacienteRepository vacunaPacienteRepository;

    public Vacuna crearVacuna(Vacuna vacuna) {
        return vacunaRepository.save(vacuna);
    }

    public Optional<Vacuna> obtenerVacunaPorId(Long id) {
        return vacunaRepository.findById(id);
    }

    public List<Vacuna> obtenerTodasLasVacunas() {
        return vacunaRepository.findAll();
    }

    public Vacuna actualizarVacuna(Long id, Vacuna vacunaActualizada) {
        Optional<Vacuna> vacunaOpt = vacunaRepository.findById(id);

        if (vacunaOpt.isPresent()) {
            Vacuna vacuna = vacunaOpt.get();
            vacuna.setNombre(vacunaActualizada.getNombre());
            vacuna.setDescripcion(vacunaActualizada.getDescripcion());
            return vacunaRepository.save(vacuna);
        } else {
            throw new RuntimeException("Vacuna no encontrada");
        }
    }

    public void eliminarVacuna(Long id) {
        Vacuna vacuna = vacunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacuna no encontrada"));

        vacunaPacienteRepository.deleteByVacunaId(id);

        vacunaRepository.delete(vacuna);
    }

}
