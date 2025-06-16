package com.neo.ssalud.models;

import com.neo.ssalud.enums.Severidad;
import com.neo.ssalud.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "ingresos", schema = "neosalud")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Ingresos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @Column(name = "numero")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "severidad")
    private Severidad severidad;

}
