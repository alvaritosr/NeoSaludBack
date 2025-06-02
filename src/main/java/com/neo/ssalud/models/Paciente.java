package com.neo.ssalud.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.neo.ssalud.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "paciente", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "anyo_nacimiento")
    private String anyoNacimiento;

    @Column(name = "dni")
    private String dni;

    @Column(name = "pasaporte")
    private String pasaporte;

    @Column(name = "nuss")
    private String nuss;

    @Column(name = "nh")
    private String nh;

    @Column(name = "nuhsa")
    private String nuhsa;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "sexo")
    private Sexo sexo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference("paciente-prescripciones")
    private Set<Prescripcion> prescripciones;

}
