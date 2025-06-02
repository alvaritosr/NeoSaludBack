package com.neo.ssalud.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilterChain;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/perfiles/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/productos/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/mensaje/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/chat/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/prescripciones").permitAll()
                        .requestMatchers("/prescripciones/**").permitAll()
                        .requestMatchers("/mensaje/**").permitAll()
                        .requestMatchers("/chat/**").permitAll()
                        .requestMatchers("/email/**").permitAll()
                        .requestMatchers("/vacunas/**").permitAll()
                        .requestMatchers("/tac/**").permitAll()
                        .requestMatchers("/prescripciones/crear").permitAll()
                        .requestMatchers("/prescripciones/paciente/**").permitAll()
                        .requestMatchers("/prescripciones/medico/**").permitAll()
                        .requestMatchers("/prescripciones").permitAll()
                        .requestMatchers("/prescripciones/**").permitAll()
                        .requestMatchers("/historial-medico/**").permitAll()
                        .requestMatchers("medicos/**").permitAll()
                        .requestMatchers("medicos/pacientes/**").permitAll()
                        .requestMatchers("/analisis-medico/**").permitAll()
                        .requestMatchers("/analisis-medico/tipos").permitAll()
                        .requestMatchers("/analisis-medico/crear").permitAll()
                        .requestMatchers("/analisis-medico/ver").permitAll()
                        .requestMatchers("/analisis-medico/nombre").permitAll()
                        .requestMatchers("/analisis-medico/analisis").permitAll()
                        .requestMatchers("/analisis-medico/analisis/**").permitAll()
                        .requestMatchers("/pacientes/**").permitAll()
                        .requestMatchers("/medicos/pacientes/**").permitAll()
                        .requestMatchers("/medicos/pacientes/buscar").permitAll()

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }
}