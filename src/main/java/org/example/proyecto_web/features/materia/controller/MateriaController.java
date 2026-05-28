package org.example.proyecto_web.features.materia.controller;

import org.example.proyecto_web.core.entidades.Materia;
import org.example.proyecto_web.features.materia.dto.MateriaRequestDTO;
import org.example.proyecto_web.features.materia.dto.MateriaResponseDTO;
import org.example.proyecto_web.features.materia.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.proyecto_web.features.tema.dto.TemaResponseDTO;
import org.example.proyecto_web.features.tema.service.TemaService;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @Autowired
    private TemaService temaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<MateriaResponseDTO> findAll() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MateriaResponseDTO findById(@PathVariable Long id) {
        return materiaService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public MateriaResponseDTO create(@RequestBody MateriaRequestDTO materiaRequestDTO) {
        return materiaService.save(materiaRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MateriaResponseDTO update(
            @PathVariable Long id,
            @RequestBody MateriaRequestDTO materiaRequestDTO
    ) {
        return materiaService.update(id, materiaRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        materiaService.deleteById(id);
    }

    @GetMapping("/{id}/temas")
    @ResponseStatus(HttpStatus.OK)
    public List<TemaResponseDTO> findTemasByMateria(@PathVariable Long id) {
        return temaService.findByMateriaId(id);
    }
}
