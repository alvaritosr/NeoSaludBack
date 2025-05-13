package com.neo.ssalud.dto;

import lombok.Data;

@Data
public class CrearChatDTO {
    private Long id;
    private Long id_creador;
    private Long id_receptor;
}
