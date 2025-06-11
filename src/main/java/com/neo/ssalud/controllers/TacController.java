package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Tac;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.repositories.tacRepository;
import com.neo.ssalud.services.TacService;
import com.neo.ssalud.services.TacService.DicomFileInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tac")
@RequiredArgsConstructor
public class TacController {

    private final TacService tacService;


    @PostMapping("/asignar")
    public ResponseEntity<String> asignarTacAPaciente(
            @RequestParam Long pacienteId,
            @RequestParam String nombreCarpeta) {

        tacService.asignarTacAPaciente(pacienteId, nombreCarpeta);

        return ResponseEntity.ok("TAC asignado correctamente al paciente con ID: " + pacienteId);
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<Tac>> obtenerTacsPorPaciente(@PathVariable Long id) {
        List<Tac> tacs = tacService.obtenerTacsPorPaciente(id);
        return ResponseEntity.ok(tacs);
    }


    @GetMapping("/{folderName}/{filename:.+}")
    public ResponseEntity<Resource> getDicomFile(@PathVariable String folderName, @PathVariable String filename) throws IOException {
        Resource file = tacService.getDicomFile(folderName, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/estudio/{folderName}")
    public ResponseEntity<List<DicomFileInfo>> getStudyFiles(@PathVariable String folderName) throws IOException {
        List<TacService.DicomFileInfo> files = tacService.getDicomStudyFiles(folderName);
        return ResponseEntity.ok(files);
    }
}
