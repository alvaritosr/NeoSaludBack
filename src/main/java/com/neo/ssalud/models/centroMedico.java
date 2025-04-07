package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "centroMedico", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class centroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @ManyToMany
    @JoinTable(name = "medico_centro_medico", joinColumns = @JoinColumn(name = "centro_medico_id"), inverseJoinColumns = @JoinColumn(name = "medico_id"))
    private Set<Medico> medicos;
}


