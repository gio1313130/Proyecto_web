package org.example.proyecto_web.config;

import org.example.proyecto_web.config.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CorsConfigurationSource corsConfigurationSource
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // CORS preflight para Angular
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Swagger / OpenAPI
                        .requestMatchers(
                                "/documentacion/swagger-ui/**",
                                "/documentacion/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/me").hasAnyRole("ALUMNO", "ADMIN")
                        // Registro público de usuarios
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()

                        // Preguntas de un cuestionario: solo ADMIN
                        // IMPORTANTE: va antes de /api/cuestionarios/*
                        .requestMatchers(HttpMethod.GET, "/api/cuestionarios/*/preguntas").hasRole("ADMIN")

                        // Resolver cuestionario: ALUMNO / ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/cuestionarios/*/resolver").hasAnyRole("ALUMNO", "ADMIN")

                        // Consultas generales de cuestionarios: ALUMNO / ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/cuestionarios").hasAnyRole("ALUMNO", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/cuestionarios/*").hasAnyRole("ALUMNO", "ADMIN")

                        // Consultas para ALUMNO / ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/materias/**").hasAnyRole("ALUMNO", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/temas/**").hasAnyRole("ALUMNO", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/recursos/**").hasAnyRole("ALUMNO", "ADMIN")

                        // Resolver intentos: ALUMNO / ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/intentos/resolver").hasAnyRole("ALUMNO", "ADMIN")

                        // Historial de intentos de usuario: ALUMNO / ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/*/intentos").hasAnyRole("ALUMNO", "ADMIN")

                        // Administración de usuarios
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // Administración de materias
                        .requestMatchers(HttpMethod.POST, "/api/materias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/materias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/materias/**").hasRole("ADMIN")

                        // Administración de temas
                        .requestMatchers(HttpMethod.POST, "/api/temas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/temas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/temas/**").hasRole("ADMIN")

                        // Administración de recursos
                        .requestMatchers(HttpMethod.POST, "/api/recursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/recursos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/recursos/**").hasRole("ADMIN")

                        // Administración de cuestionarios
                        .requestMatchers(HttpMethod.POST, "/api/cuestionarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/cuestionarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/cuestionarios/**").hasRole("ADMIN")

                        // Preguntas y opciones: solo ADMIN
                        .requestMatchers("/api/preguntas/**").hasRole("ADMIN")
                        .requestMatchers("/api/opciones/**").hasRole("ADMIN")

                        // Intentos generales: solo ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/intentos/**").hasRole("ADMIN")

                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}