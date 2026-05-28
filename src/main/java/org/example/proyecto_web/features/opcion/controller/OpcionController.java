package org.example.proyecto_web.features.opcion.controller;


import org.example.proyecto_web.features.opcion.dto.OpcionRequestDTO;
import org.example.proyecto_web.features.opcion.dto.OpcionResponseDTO;
import org.example.proyecto_web.features.opcion.service.OpcionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opciones")
public class OpcionController {
    @Autowired
    private OpcionService opcionService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<OpcionResponseDTO> findAll(){

        return opcionService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpcionResponseDTO findById(@PathVariable Long id){
        return opcionService.findById(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public OpcionResponseDTO create(@RequestBody OpcionRequestDTO opcionRequestDTO){

        return opcionService.save(opcionRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OpcionResponseDTO update(@PathVariable Long id, @RequestBody OpcionRequestDTO opcionRequestDTO){

        return opcionService.update(id,opcionRequestDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        opcionService.deleteById(id);
    }
}
