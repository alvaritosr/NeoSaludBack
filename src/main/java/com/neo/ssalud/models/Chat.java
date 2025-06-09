package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_creador", nullable = false)
    private Medico creador;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private Medico receptor;
}
