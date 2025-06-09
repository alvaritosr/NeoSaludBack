package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje", schema = "neosalud")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "id_emisor", nullable = false)
    private Medico emisor;

    @ManyToOne
    @JoinColumn(name = "id_receptor", nullable = false)
    private Medico receptor;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fecha")
    private LocalDateTime fecha;
}
