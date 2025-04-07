package com.neo.ssalud.dto;

import com.neo.ssalud.enums.Especialidad;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroDTO {

    //TABLA PERFIL
    private Integer Numero_colegiado;
    private String nombre;
    private String apellidos;
    private String ubicacion;
    private String telefono;
    private boolean baneado = false;

    // TABLA USUARIO
    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    private String email;
    private String username;
    private String password;
    private Especialidad especialidad = Especialidad.Cardiología;

}