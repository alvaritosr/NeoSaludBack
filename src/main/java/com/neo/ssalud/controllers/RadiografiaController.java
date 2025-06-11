package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Radiografia;
import com.neo.ssalud.services.RadiografiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Resource> getDicomFile(@PathVariable String nombreArchivo) throws IOException {
        try {
            // Log para verificar el nombre del archivo solicitado
            System.out.println("Solicitando archivo DICOM: " + nombreArchivo);

            Resource file = radiografiaService.getDicomFileAsResource(nombreArchivo);

            // Log para verificar si el archivo fue recuperado correctamente
            if (file.exists() && file.isReadable()) {
                System.out.println("Archivo recuperado exitosamente: " + file.getFilename());
            } else {
                System.out.println("El archivo no existe o no es legible: " + nombreArchivo);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            // Log para capturar cualquier excepción
            System.err.println("Error al recuperar el archivo DICOM: " + e.getMessage());
            e.printStackTrace();

            // Retornar un error 500 en caso de excepción
            return ResponseEntity.status(500).build();
        }
    }


}