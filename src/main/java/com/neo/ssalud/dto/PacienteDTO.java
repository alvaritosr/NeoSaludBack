package com.neo.ssalud.dto;

import com.neo.ssalud.enums.Sexo;
import lombok.Data;

import java.util.Date;

@Data
public class PacienteDTO {
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String anyoNacimiento;
    private String dni;
    private String pasaporte;
    private String nuss;
    private String nh;
    private String nuhsa;
    private Date fecha;
    private Sexo sexo;
    private String direccion;
    private String telefono;
    private String email;
}
