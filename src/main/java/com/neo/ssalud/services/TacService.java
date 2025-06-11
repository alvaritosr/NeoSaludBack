package com.neo.ssalud.services;

import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.models.Tac;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.repositories.tacRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class TacService {

    private final tacRepository tacRepository;
    private final pacienteRepository pacienteRepository;

    private final Path dicomStoragePath = Paths.get("/data/dicom/");

    @Value("${dicom.base-path}")
    private String dicomBasePath;

    public Tac asignarTacAPaciente(Long pacienteId, String nombreCarpeta) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId));

        Tac nuevoTac = new Tac();
        nuevoTac.setNombreCarpeta(nombreCarpeta);
        nuevoTac.setPaciente(paciente);

        return tacRepository.save(nuevoTac);
    }

    // Listar los TAC de un paciente
    public List<Tac> obtenerTacsPorPaciente(Long pacienteId) {
        if (!pacienteRepository.existsById(pacienteId)) {
            throw new EntityNotFoundException("Paciente no encontrado con ID: " + pacienteId);
        }
        return tacRepository.findByPacienteId(pacienteId);
    }

    public Resource getDicomFile(String folderName, String filename) throws IOException {
        Path filePath = Paths.get(dicomBasePath, folderName, filename);
        if (!Files.exists(filePath)) {
            System.out.println("Not found: " + filePath);
            throw new IOException("DICOM file not found: " + filename);
        }
        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() || file.isReadable()) {
            return file;
        } else {
            throw new RuntimeException("No se pudo leer el archivo DICOM");
        }
    }


    public List<DicomFileInfo> getDicomStudyFiles(String folderName) throws IOException {
        File folder = new File(dicomBasePath, folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IOException("Folder not found: " + folderName);
        }

        List<DicomFileInfo> dicomFiles = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder.toPath())) {
            for (Path filePath : stream) {
                if (Files.isRegularFile(filePath)) {
                    try (DicomInputStream dis = new DicomInputStream(filePath.toFile())) {
                        Attributes attr = dis.readDataset(-1, -1);
                        int instanceNumber = attr.getInt(Tag.InstanceNumber, 0);
                        dicomFiles.add(new DicomFileInfo(filePath.getFileName().toString(), instanceNumber));
                    } catch (Exception e) {
                        // Loggear o ignorar archivos no DICOM
                    }
                }
            }
        }

        dicomFiles.sort(Comparator.comparingInt(DicomFileInfo::getInstanceNumber));
        return dicomFiles;
    }

    @Data
    @AllArgsConstructor
    public static class DicomFileInfo {
        private String fileName;
        private int instanceNumber;
    }
}
