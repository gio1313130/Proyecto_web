package org.example.proyecto_web.features.intento.controller;

import org.example.proyecto_web.core.entidades.Intento;
import org.example.proyecto_web.features.intento.dto.IntentoRequestDTO;
import org.example.proyecto_web.features.intento.dto.IntentoResponseDTO;
import org.example.proyecto_web.features.intento.dto.ResolverCuestionarioRequestDTO;
import org.example.proyecto_web.features.intento.dto.ResolverCuestionarioResponseDTO;
import org.example.proyecto_web.features.intento.service.IntentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intentos")
public class IntentoController {
    @Autowired
    private IntentoService intentoService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<IntentoResponseDTO> findAll() {
        return intentoService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IntentoResponseDTO findById(@PathVariable Long id) {
        return intentoService.findById(id);
    }


    @PostMapping("/resolver")
    @ResponseStatus(HttpStatus.CREATED)
    public ResolverCuestionarioResponseDTO resolverCuestionario(
            @RequestBody ResolverCuestionarioRequestDTO requestDTO
    ) {
        return intentoService.resolverCuestionario(requestDTO);
    }
}
