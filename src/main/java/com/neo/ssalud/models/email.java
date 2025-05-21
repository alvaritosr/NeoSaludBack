package com.neo.ssalud.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinatario;
    private String asunto;
    private String contenido;
    private LocalDateTime fechaEnvio;
}