package com.neo.ssalud.security;

import com.neo.ssalud.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDataDTO {
    private Long id;
    private String username;
    private Rol rol;
    private Long fecha_creacion;
    private Long fecha_expiracion;
}