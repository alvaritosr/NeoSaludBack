package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recetaMedicacion", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class recetaMedicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "medicacion")
    private String medicacion;

    @Column(name = "dosis")
    private String dosis;

    @Column(name = "duracion")
    private String duracion;

    @ManyToOne
    @JoinColumn(name = "paciente", nullable = false)
    private Paciente paciente;
}