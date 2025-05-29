package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tac", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombreCarpeta")
    private String nombreCarpeta;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

}
