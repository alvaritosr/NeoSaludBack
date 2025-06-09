package com.neo.ssalud.dto;

import com.neo.ssalud.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroDTO {

    private Integer numero_colegiado;
    private String nombre;
    private String apellidos;
    private Rol rol;
    private String email;
    private String username;
    private String password;

}