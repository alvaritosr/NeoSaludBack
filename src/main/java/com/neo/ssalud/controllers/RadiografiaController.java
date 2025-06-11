package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Radiografia;
import com.neo.ssalud.services.RadiografiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/radiografia")
@RequiredArgsConstructor
public class RadiografiaController {

    private final RadiografiaService radiografiaService;

    @Value("${dicom.base-path}")
    private String dicomBasePath;

    @PostMapping("/asignar")
    public ResponseEntity<String> asignarRadiografiaAPaciente(
            @RequestParam Long pacienteId,
            @RequestParam String nombreArchivo) {

        radiografiaService.asignarRadiografiaAPaciente(pacienteId, nombreArchivo);

        return ResponseEntity.ok("Radiografía asignada correctamente al paciente con ID: " + pacienteId);
    }

    @GetMapping("/paciente/{id}")
    public List<Radiografia> obtenerRadiografiasPorPaciente(@PathVariable Long id) {
        List<Radiografia> radiografias = radiografiaService.obtenerRadiografiasPorPaciente(id);
        return radiografias.stream()
                .map(r -> new Radiografia(
                        r.getId(),
                        r.getNombreArchivo(),
                        r.getPaciente()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/dicom/{nombreArchivo}", produces = "application/dicom")
    public ResponseEntity<Resource> getDicomFile(@PathVariable String nombreArchivo) {
        System.out.println("Iniciando la obtención del archivo DICOM: " + nombreArchivo);

        try {
            Resource file = radiografiaService.getDicomFileAsResource(nombreArchivo);
            System.out.println("Archivo obtenido correctamente: " + (file != null ? file.getFilename() : "null"));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (IOException e) {
            System.err.println("Error al obtener el archivo DICOM: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo DICOM no encontrado: " + nombreArchivo);
        }
    }
}