package org.example.proyecto_web.features.tema.controller;

import org.example.proyecto_web.core.entidades.Tema;
import org.example.proyecto_web.features.tema.dto.TemaRequestDTO;
import org.example.proyecto_web.features.tema.dto.TemaResponseDTO;
import org.example.proyecto_web.features.tema.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.proyecto_web.features.recurso.dto.RecursoResponseDTO;
import org.example.proyecto_web.features.recurso.service.RecursoService;
import org.example.proyecto_web.features.cuestionario.dto.CuestionarioResponseDTO;
import org.example.proyecto_web.features.cuestionario.service.CuestionarioService;

import java.util.List;

@RestController
@RequestMapping("/api/temas")
public class TemaController {
    @Autowired
    private TemaService temaService;

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private CuestionarioService cuestionarioService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TemaResponseDTO> findAll() {
        return temaService.findAll();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TemaResponseDTO findById(@PathVariable Long id) {
        return temaService.findById(id);
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TemaResponseDTO create(@RequestBody TemaRequestDTO temaRequestDTO) {
        return temaService.save(temaRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TemaResponseDTO update(
            @PathVariable Long id,
            @RequestBody TemaRequestDTO temaRequestDTO
    ) {
        return temaService.update(id, temaRequestDTO);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        temaService.deleteById(id);
    }


    @GetMapping("/{id}/recursos")
    @ResponseStatus(HttpStatus.OK)
    public List<RecursoResponseDTO> findRecursosByTema(@PathVariable Long id) {
        return recursoService.findByTemaId(id);
    }

    @GetMapping("/{id}/cuestionarios")
    @ResponseStatus(HttpStatus.OK)
    public List<CuestionarioResponseDTO> findCuestionariosByTema(@PathVariable Long id) {
        return cuestionarioService.findByTemaId(id);
    }
}
