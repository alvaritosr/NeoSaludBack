package com.neo.ssalud.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {
    private Long id;
    private LocalDateTime fechaConsulta;

    public ConsultaDTO(Long id, LocalDateTime fechaConsulta) {
        this.id = id;
        this.fechaConsulta = fechaConsulta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }
}