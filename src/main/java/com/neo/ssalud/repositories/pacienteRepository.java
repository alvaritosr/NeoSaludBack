package com.neo.ssalud.repositories;

import com.neo.ssalud.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface pacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Object> findByNh(String nh);

    @Query("SELECT p FROM Paciente p WHERE " +
            "(:nombre IS NULL OR p.nombre LIKE %:nombre%) AND " +
            "(:primerApellido IS NULL OR p.primerApellido LIKE %:primerApellido%) AND " +
            "(:segundoApellido IS NULL OR p.segundoApellido LIKE %:segundoApellido%) AND " +
            "(:dni IS NULL OR p.dni = :dni) AND " +
            "(:pasaporte IS NULL OR p.pasaporte = :pasaporte) AND " +
            "(:nuss IS NULL OR p.nuss = :nuss) AND " +
            "(:nh IS NULL OR p.nh = :nh) AND " +
            "(:nuhsa IS NULL OR p.nuhsa = :nuhsa) AND " +
            "(:anyoNacimiento IS NULL OR p.anyoNacimiento = :anyoNacimiento) AND " +
            "(:direccion IS NULL OR p.direccion LIKE %:direccion%)")
    List<Paciente> buscarPacientes(
            @Param("nombre") String nombre,
            @Param("primerApellido") String primerApellido,
            @Param("segundoApellido") String segundoApellido,
            @Param("dni") String dni,
            @Param("pasaporte") String pasaporte,
            @Param("nuss") String nuss,
            @Param("nh") String nh,
            @Param("nuhsa") String nuhsa,
            @Param("anyoNacimiento") String anyoNacimiento,
            @Param("direccion") String direccion);
}
