package com.neo.ssalud.dto;

import lombok.Data;

@Data
public class MensajeDTO {
    private Long id;
    private Long idChat;
    private Long idEmisor;
    private Long idReceptor;
    private String contenido;
    private String fecha;
}
