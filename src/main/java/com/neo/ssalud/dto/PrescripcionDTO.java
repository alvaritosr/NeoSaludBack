package com.neo.ssalud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class PrescripcionDTO {
    private Long id;
    private String nombrePrescriptor;
    private String numeroColegiado;
    private String especialidad;
    private String firmaDigital;
    private String contactoPrescriptor;
    private String nombreGenerico;
    private String nombreComercial;
    private String concentracion;
    private String formaFarmaceutica;
    private String viaAdministracion;
    private String dosis;
    private String frecuencia;
    private int duracionDias;
    private int cantidadEnvases;
    private String lugarEmision;
    private LocalDate fechaEmision;
    private String indicacionesEspecificas;
    private String notasAdicionales;
    private Long medicoId;
    private Long pacienteId;

    public PrescripcionDTO(Long id, String nombrePrescriptor, String numeroColegiado, String especialidad, String firmaDigital, String contactoPrescriptor, String nombreGenerico, String nombreComercial, String concentracion, String formaFarmaceutica, String viaAdministracion, String dosis, String frecuencia, int duracionDias, int cantidadEnvases, String lugarEmision, LocalDate fechaEmision, String indicacionesEspecificas, String notasAdicionales, Long medicoId, Long pacienteId) {
        this.id = id;
        this.nombrePrescriptor = nombrePrescriptor;
        this.numeroColegiado = numeroColegiado;
        this.especialidad = especialidad;
        this.firmaDigital = firmaDigital;
        this.contactoPrescriptor = contactoPrescriptor;
        this.nombreGenerico = nombreGenerico;
        this.nombreComercial = nombreComercial;
        this.concentracion = concentracion;
        this.formaFarmaceutica = formaFarmaceutica;
        this.viaAdministracion = viaAdministracion;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.duracionDias = duracionDias;
        this.cantidadEnvases = cantidadEnvases;
        this.lugarEmision = lugarEmision;
        this.fechaEmision = fechaEmision;
        this.indicacionesEspecificas = indicacionesEspecificas;
        this.notasAdicionales = notasAdicionales;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
    }

}