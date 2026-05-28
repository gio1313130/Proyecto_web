package org.example.proyecto_web.features.usuario.controller;


import jakarta.validation.Valid;
import org.example.proyecto_web.core.entidades.Usuario;
import org.example.proyecto_web.features.usuario.dto.UsuarioRequestDTO;
import org.example.proyecto_web.features.usuario.dto.UsuarioResponseDTO;
import org.example.proyecto_web.features.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.proyecto_web.features.intento.dto.IntentoResponseDTO;
import org.example.proyecto_web.features.intento.service.IntentoService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private IntentoService intentoService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioService.findAll();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO save(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        return usuarioService.save(usuarioRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO update(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO usuarioRequestDTO
    ) {
        return usuarioService.update(id, usuarioRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

    @GetMapping("/{id}/intentos")
    @ResponseStatus(HttpStatus.OK)
    public List<IntentoResponseDTO> findIntentosByUsuario(@PathVariable Long id) {
        return intentoService.findByUsuarioId(id);
    }

}
