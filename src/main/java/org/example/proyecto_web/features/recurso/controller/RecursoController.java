package org.example.proyecto_web.features.recurso.controller;

import org.example.proyecto_web.features.recurso.dto.RecursoResponseDTO;
import org.example.proyecto_web.features.recurso.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.proyecto_web.features.recurso.dto.RecursoRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<RecursoResponseDTO> findAll() {
        return recursoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecursoResponseDTO findById(@PathVariable Long id) {
        return recursoService.findById(id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RecursoResponseDTO upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("tituloRecurso") String tituloRecurso,
            @RequestParam("tipoRecurso") String tipoRecurso,
            @RequestParam("autor") String autor,
            @RequestParam(value = "descripcionRecurso", required = false) String descripcionRecurso,
            @RequestParam("idTema") Long idTema
    ) {
        return recursoService.upload(
                file,
                tituloRecurso,
                tipoRecurso,
                autor,
                descripcionRecurso,
                idTema
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        recursoService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecursoResponseDTO update(
            @PathVariable Long id,
            @RequestBody RecursoRequestDTO recursoRequestDTO
    ) {
        return recursoService.update(id, recursoRequestDTO);
    }
}