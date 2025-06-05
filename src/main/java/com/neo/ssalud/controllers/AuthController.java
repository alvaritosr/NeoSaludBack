package com.neo.ssalud.controllers;

import com.neo.ssalud.dto.LoginDTO;
import com.neo.ssalud.dto.RegistroDTO;
import com.neo.ssalud.dto.RespuestaDTO;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.services.medicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private medicoService service;

    @PostMapping("/registro")
    public Medico registro(@RequestBody RegistroDTO registroDTO){
        return service.registrarUsuario(registroDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> login(@RequestBody LoginDTO dto){
        return service.login(dto);
    }

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<?> recuperarContrasena(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        service.recuperarContrasena(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restablecer-contrasena")
    public ResponseEntity<?> restablecerContrasena(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        service.restablecerContrasena(token, newPassword);
        return ResponseEntity.ok().build();
    }
}
