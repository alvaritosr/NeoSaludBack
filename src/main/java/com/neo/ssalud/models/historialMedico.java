package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "historialMedico", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class historialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "alergias")
    private String alergias;

    @Column(name = "enfermedadesCronicas")
    private String motivo;

    @Column(name = "antecedentes")
    private String antecedentes;

    @ManyToOne
    @JoinColumn(name = "paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "pruebasYdiagnosticos", nullable = false)
    private pruebasYdiagnosticos pruebasYdiagnosticos;
}
