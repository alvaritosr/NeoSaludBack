package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ResultadoAnalisis", schema = "neosalud")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResultadoAnalisis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "analisisMedico", nullable = false)
    private AnalisisMedico analisisMedico;

    @Column(name = "parametro")
    private String parametro;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "valor_referencia")
    private Double valorReferencia;

}
