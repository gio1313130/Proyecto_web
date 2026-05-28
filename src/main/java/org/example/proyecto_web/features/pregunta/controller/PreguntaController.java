package org.example.proyecto_web.features.pregunta.controller;

import org.example.proyecto_web.features.pregunta.dto.PreguntaRequestDTO;
import org.example.proyecto_web.features.pregunta.dto.PreguntaResponseDTO;
import org.example.proyecto_web.features.pregunta.service.PreguntaService;
import org.example.proyecto_web.features.opcion.dto.OpcionResponseDTO;
import org.example.proyecto_web.features.opcion.service.OpcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {
    @Autowired
    private PreguntaService preguntaService;

    @Autowired
    private OpcionService opcionService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PreguntaResponseDTO> findAll(){

        return preguntaService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PreguntaResponseDTO findById(@PathVariable Long id){
        return preguntaService.findById(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PreguntaResponseDTO create(@RequestBody PreguntaRequestDTO preguntaRequestDTO){

        return preguntaService.save(preguntaRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PreguntaResponseDTO update(@PathVariable Long id, @RequestBody PreguntaRequestDTO preguntaRequestDTO){

        return preguntaService.update(id,preguntaRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        preguntaService.deleteById(id);
    }

    @GetMapping("/{id}/opciones")
    @ResponseStatus(HttpStatus.OK)
    public List<OpcionResponseDTO> findOpcionesByPregunta(@PathVariable Long id) {
        return opcionService.findByPreguntaId(id);
    }

}







