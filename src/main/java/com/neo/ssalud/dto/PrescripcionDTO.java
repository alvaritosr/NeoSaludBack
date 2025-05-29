package com.neo.ssalud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Getter
@Setter
public class PrescripcionDTO {
    private Long id;
    private String nombreComercial;
    private String nombreGenerico;
    private String medicoNombre;
    private String pacienteNombre;
    private LocalDate fechaEmision;
    private String dosis;
    private String frecuencia;
    private int duracion;
    private String observaciones;
}