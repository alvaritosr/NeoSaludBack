package com.neo.ssalud.services;

import com.neo.ssalud.dto.LoginDTO;
import com.neo.ssalud.dto.PacienteDTO;
import com.neo.ssalud.dto.RegistroDTO;
import com.neo.ssalud.dto.RespuestaDTO;
import com.neo.ssalud.models.Consulta;
import com.neo.ssalud.models.Medico;
import com.neo.ssalud.models.Paciente;
import com.neo.ssalud.repositories.ConsultaRepository;
import com.neo.ssalud.repositories.medicoRepository;
import com.neo.ssalud.repositories.pacienteRepository;
import com.neo.ssalud.security.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Validated
@AllArgsConstructor
public class medicoService implements UserDetailsService {

    private final medicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final pacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    private final Map<String, String> resetTokens = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> tokenExpiryDates = new ConcurrentHashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return medicoRepository.findTopByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Usuario no encontrado con el nombre: " + username));
    }

    public Medico verDetalleMedico(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medico not found with ID: " + id));
    }

    public Medico registrarUsuario(RegistroDTO dto) {
        if (medicoRepository.findTopByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario '" + dto.getUsername() + "' ya está en uso.");
        }

        Medico nuevoMedico = new Medico();
        nuevoMedico.setUsername(dto.getUsername());
        nuevoMedico.setEmail(dto.getEmail());
        nuevoMedico.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Nuevos campos
        nuevoMedico.setNombre(dto.getNombre());
        nuevoMedico.setApellidos(dto.getApellidos());
        nuevoMedico.setNumero_colegiado(dto.getNumero_colegiado());
        nuevoMedico.setRol(dto.getRol()); // Set the admin field

        return medicoRepository.save(nuevoMedico);
    }


    public ResponseEntity<RespuestaDTO> login(LoginDTO dto) {
        Optional<Medico> usuarioOpcional = medicoRepository.findTopByUsername(dto.getUsername());

        if (usuarioOpcional.isPresent()) {
            Medico usuario = usuarioOpcional.get();

            if (passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
                String token = jwtService.generateToken(usuario);
                return ResponseEntity
                        .ok(RespuestaDTO
                                .builder()
                                .estado(HttpStatus.OK.value())
                                .token(token).build());
            } else {
                throw new BadCredentialsException("Contraseña incorrecta");
            }
        } else {
            throw new NoSuchElementException("Usuario no encontrado");
        }
    }

    public void recuperarContrasena(String email) {
        Optional<Medico> usuarioOptional = medicoRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Medico usuario = usuarioOptional.get();
            String token = UUID.randomUUID().toString();
            // resetTokens.put(token, usuario.getUsername());
            tokenExpiryDates.put(token, LocalDateTime.now().plusHours(1));

            String resetLink = "http://localhost:4200/restablecer-contrasena?token=" + token;
            String emailContent = "<html>" +
                    "<body style=\"padding: 20px; font-family: Arial, sans-serif;\">" +
                    "<div style=\"max-width: 600px; margin: auto; background: #e6a1f1; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">" +
                    "<h1 style=\"color: #333;\">Recuperación de Contraseña</h1>" +
                    "<p>Hola " + usuario.getUsername() + ",</p>" +
                    "<p>Hemos recibido una solicitud para restablecer tu contraseña. Haz clic en el botón de abajo para restablecerla:</p>" +
                    "<a href=\"" + resetLink + "\" style=\"display: inline-block; padding: 15px 30px; font-size: 18px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;\">Restablecer Contraseña</a>" +
                    "<p>Si no solicitaste un cambio de contraseña, puedes ignorar este correo.</p>" +
                    "<p>Gracias.</p>" +
                    "<p>El equipo de neo.ssalud</p>" +
                    "<hr>" +
                    "<p><small>Al hacer clic en el botón, aceptas nuestros <a href=\"http://localhost:4200/terminos\">Términos de Servicio</a> y <a href=\"http://localhost:4200/privacidad\">Política de Privacidad</a>.</small></p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            emailService.enviarEmailRecuperacion(email, "Recuperación de Contraseña", emailContent);
        } else {
            throw new NoSuchElementException("Usuario no encontrado con el correo: " + email);
        }
    }

    public void restablecerContrasena(String token, String newPassword) {
        String username = resetTokens.get(token);
        LocalDateTime expiryDate = tokenExpiryDates.get(token);

        if (username != null && expiryDate != null && expiryDate.isAfter(LocalDateTime.now())) {
            Optional<Medico> usuarioOptional = medicoRepository.findTopByUsername(username);
            if (usuarioOptional.isPresent()) {
                Medico usuario = usuarioOptional.get();
                usuario.setPassword(passwordEncoder.encode(newPassword));
                medicoRepository.save(usuario);

                resetTokens.remove(token);
                tokenExpiryDates.remove(token);
            } else {
                throw new NoSuchElementException("Usuario no encontrado");
            }
        } else {
            throw new IllegalArgumentException("Token no válido o ha expirado");
        }
    }

    public Paciente crearPaciente(PacienteDTO pacienteDTO, String usernameMedico) {

        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));
        System.out.println("Creando paciente con datos: " + pacienteDTO + " y médico: " + medico.getUsername());

        Paciente paciente = new Paciente();
        paciente.setNombre(pacienteDTO.getNombre());
        paciente.setPrimerApellido(pacienteDTO.getPrimerApellido());
        paciente.setSegundoApellido(pacienteDTO.getSegundoApellido());
        paciente.setAnyoNacimiento(pacienteDTO.getAnyoNacimiento());
        paciente.setDni(pacienteDTO.getDni());
        paciente.setPasaporte(pacienteDTO.getPasaporte());
        paciente.setNuss(pacienteDTO.getNuss());
        paciente.setNh(pacienteDTO.getNh());
        paciente.setNuhsa(pacienteDTO.getNuhsa());
        paciente.setFecha(pacienteDTO.getFecha());
        paciente.setSexo(pacienteDTO.getSexo());
        paciente.setDireccion(pacienteDTO.getDireccion());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setMedico(medico);


        return pacienteRepository.save(paciente);
    }

    public Paciente cambiarMedicoDePaciente(String nh, String nuevoUsernameMedico) {
        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        Medico nuevoMedico = medicoRepository.findTopByUsername(nuevoUsernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + nuevoUsernameMedico));

        paciente.setMedico(nuevoMedico);

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarPacientes(String nombre, String primerApellido, String segundoApellido, String dni,
                                          String pasaporte, String nuss, String nh, String nuhsa,
                                          String anyoNacimiento, String direccion) {
        return pacienteRepository.buscarPacientes(
                nombre, primerApellido, segundoApellido, dni, pasaporte, nuss, nh, nuhsa, anyoNacimiento, direccion);
    }

    public Consulta crearConsulta(String nhPaciente, Consulta consulta) {
        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        consulta.setPaciente(paciente);
        consulta.setFechaConsulta(LocalDateTime.now());

        return consultaRepository.save(consulta);
    }

    public List<Consulta> verConsulta(String nhPaciente, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return consultaRepository.findByPaciente(paciente);
    }

    public Consulta verDetalleConsulta(String nhPaciente, Long idConsulta, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nhPaciente)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nhPaciente));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new NoSuchElementException("Consulta no encontrada con el id: " + idConsulta));

        if (!consulta.getPaciente().getId().equals(paciente.getId())) {
            throw new IllegalArgumentException("La consulta no pertenece al paciente especificado.");
        }

        return consulta;
    }

    public Paciente verDetallePaciente(String nh, String usernameMedico) {
        Medico medico = medicoRepository.findTopByUsername(usernameMedico)
                .orElseThrow(() -> new NoSuchElementException("Médico no encontrado con el nombre: " + usernameMedico));

        Paciente paciente = (Paciente) pacienteRepository.findByNh(nh)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con el nh: " + nh));

        if (!paciente.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("El paciente no está asociado al médico autenticado.");
        }

        return paciente;
    }

}