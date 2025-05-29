package com.neo.ssalud.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescripcion", schema = "neosalud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrePrescriptor;
    private String numeroColegiado;
    private String especialidad;
    private String firmaDigital;
    private String contactoPrescriptor;

    private String nombreGenerico;
    private String nombreComercial;
    private String concentracion;
    private String formaFarmaceutica;
    private String viaAdministracion;
    private String dosis;
    private String frecuencia;
    private int duracionDias;
    private int cantidadEnvases;

    private String lugarEmision;
    @Column(name = "fecha_emision", columnDefinition = "date")
    private LocalDate fechaEmision;
    private String indicacionesEspecificas;
    private String notasAdicionales;

    @ManyToOne
    @JsonBackReference("medico-prescripciones")
    private Medico medico;

    @ManyToOne
    @JsonBackReference("paciente-prescripciones")
    private Paciente paciente;
}