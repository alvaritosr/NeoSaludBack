package com.neo.ssalud.services;

import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Radiografia;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.repositories.RadiografiaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RadiografiaService {

    private final RadiografiaRepository radiografiaRepository;
    private final pacienteRepository pacienteRepository;

    @Value("${dicom.base-path}")
    private String dicomBasePath;

    public void asignarRadiografiaAPaciente(Long pacienteId, String nombreArchivo) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Radiografia radiografia = new Radiografia();
        radiografia.setPaciente(paciente);
        radiografia.setNombreArchivo(nombreArchivo);

        radiografiaRepository.save(radiografia);
    }

    public List<Radiografia> obtenerRadiografiasPorPaciente(Long pacienteId) {
        return radiografiaRepository.findByPacienteId(pacienteId);
    }

    public Resource getDicomFileAsResource(String nombreArchivo) throws IOException {
        Path filePath = Paths.get(dicomBasePath + "/" + nombreArchivo);

        if (!Files.exists(filePath)) {
            System.out.println("Not found: " + filePath);
            throw new IOException("DICOM file not found: " + nombreArchivo);
        }

        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() || file.isReadable()) {
            return file;
        } else {
            throw new RuntimeException("No se pudo leer el archivo DICOM");
        }
    }
}