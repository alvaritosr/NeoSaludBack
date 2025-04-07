package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "citamedica", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class citaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "motivo")
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "medico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente", nullable = false)
    private Paciente paciente;
}
