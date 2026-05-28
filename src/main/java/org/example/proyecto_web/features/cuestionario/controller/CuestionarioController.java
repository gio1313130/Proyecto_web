package org.example.proyecto_web.features.cuestionario.controller;

import org.example.proyecto_web.core.entidades.Cuestionario;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioRequestDTO;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioResolverDTO;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioResponseDTO;
import org.example.proyecto_web.features.cuestionario.service.CuestionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.proyecto_web.features.pregunta.dto.PreguntaResponseDTO;
import org.example.proyecto_web.features.pregunta.service.PreguntaService;

import java.util.List;

@RestController
@RequestMapping("/api/cuestionarios")
public class CuestionarioController {
    @Autowired
    private CuestionarioService cuestionarioService;

    @Autowired
    private PreguntaService preguntaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CuestionarioResponseDTO> findAll() {
        return cuestionarioService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CuestionarioResponseDTO findById(@PathVariable Long id) {
        return cuestionarioService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CuestionarioResponseDTO create(@RequestBody CuestionarioRequestDTO cuestionarioRequestDTO) {
        return cuestionarioService.save(cuestionarioRequestDTO);
    }

    @PutMapping("/{id}")
    public CuestionarioResponseDTO update(
            @PathVariable Long id,
            @RequestBody CuestionarioRequestDTO cuestionarioRequestDTO
    ) {
        return cuestionarioService.update(id, cuestionarioRequestDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        cuestionarioService.deleteById(id);
    }

    @GetMapping("/{id}/resolver")
    @ResponseStatus(HttpStatus.OK)
    public CuestionarioResolverDTO obtenerParaResolver(@PathVariable Long id) {
        return cuestionarioService.obtenerParaResolver(id);
    }

    @GetMapping("/{id}/preguntas")
    @ResponseStatus(HttpStatus.OK)
    public List<PreguntaResponseDTO> findPreguntasByCuestionario(@PathVariable Long id) {
        return preguntaService.findByCuestionarioId(id);
    }

}
