package com.neo.ssalud.controllers;

import com.neo.ssalud.models.Ingresos;
import com.neo.ssalud.services.IngresosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingresos")
public class IngresosController {

    @Autowired
    private IngresosService ingresosService;

    @PostMapping
    public ResponseEntity<Ingresos> crearIngreso(
            @RequestParam String nh,
            @RequestParam String username,
            @RequestBody Ingresos ingreso) {
        Ingresos nuevoIngreso = ingresosService.crearIngreso(nh, username, ingreso);
        return new ResponseEntity<>(nuevoIngreso, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarIngreso(
            @PathVariable Long id,
            @RequestParam String nh,
            @RequestParam String username,
            @RequestBody Ingresos ingresoActualizado) {
        try {
            Ingresos ingresoModificado = ingresosService.modificarIngreso(id, nh, username, ingresoActualizado);
            return new ResponseEntity<>(ingresoModificado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> verIngresos(@RequestParam String username) {
        try {
            return new ResponseEntity<>(ingresosService.verIngresos(username), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingresos> verIngresoPorId(@PathVariable Long id, @RequestParam String username) {
        Ingresos ingreso = ingresosService.verIngresoPorId(id, username);
        return ResponseEntity.ok(ingreso);
    }
}