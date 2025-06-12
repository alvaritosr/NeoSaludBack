package com.neo.ssalud.services;

import com.neo.ssalud.models.ResultadoAnalisis;
import com.neo.ssalud.models.AnalisisMedico;
import com.neo.ssalud.repositories.ResultadoAnalisisRepository;
import com.neo.ssalud.repositories.AnalisisMedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultadoAnalisisService {

    private final ResultadoAnalisisRepository resultadoAnalisisRepository;
    private final AnalisisMedicoRepository analisisMedicoRepository;

    public ResultadoAnalisis crearResultadoAnalisis(Long analisisMedicoId, String parametro, Double valor, String unidad, Double valorReferencia) {
        AnalisisMedico analisisMedico = analisisMedicoRepository.findById(analisisMedicoId)
                .orElseThrow(() -> new EntityNotFoundException("Análisis médico no encontrado con ID: " + analisisMedicoId));

        ResultadoAnalisis resultadoAnalisis = new ResultadoAnalisis();
        resultadoAnalisis.setAnalisisMedico(analisisMedico);
        resultadoAnalisis.setParametro(parametro);
        resultadoAnalisis.setValor(valor);
        resultadoAnalisis.setUnidad(unidad);
        resultadoAnalisis.setValorReferencia(valorReferencia);

        return resultadoAnalisisRepository.save(resultadoAnalisis);
    }

    public List<ResultadoAnalisis> verResultadosPorAnalisisMedico(Long analisisMedicoId) {
        return resultadoAnalisisRepository.findByAnalisisMedicoId(analisisMedicoId);
    }
}