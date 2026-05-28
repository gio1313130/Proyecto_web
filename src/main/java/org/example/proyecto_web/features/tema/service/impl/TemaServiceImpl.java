package org.example.proyecto_web.features.tema.service.impl;

import org.example.proyecto_web.core.entidades.Materia;
import org.example.proyecto_web.core.entidades.Tema;
import org.example.proyecto_web.features.materia.repository.MateriaRepository;
import org.example.proyecto_web.features.tema.dto.TemaRequestDTO;
import org.example.proyecto_web.features.tema.dto.TemaResponseDTO;
import org.example.proyecto_web.features.tema.repository.TemaRepository;
import org.example.proyecto_web.features.tema.service.TemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TemaServiceImpl implements TemaService {
    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TemaResponseDTO> findAll() {
        return temaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TemaResponseDTO findById(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));

        return toResponseDTO(tema);
    }


    @Override
    @Transactional
    public TemaResponseDTO save(TemaRequestDTO temaRequestDTO) {
        Materia materia = materiaRepository.findById(temaRequestDTO.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + temaRequestDTO.getIdMateria()));

        Tema tema = new Tema();
        tema.setNombreTema(temaRequestDTO.getNombreTema());
        tema.setDescripcionTema(temaRequestDTO.getDescripcionTema());
        tema.setMateria(materia);

        Tema temaGuardado = temaRepository.save(tema);

        return toResponseDTO(temaGuardado);
    }

    @Override
    @Transactional
    public TemaResponseDTO update(Long id, TemaRequestDTO temaRequestDTO) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + id));

        Materia materia = materiaRepository.findById(temaRequestDTO.getIdMateria())
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + temaRequestDTO.getIdMateria()));

        tema.setNombreTema(temaRequestDTO.getNombreTema());
        tema.setDescripcionTema(temaRequestDTO.getDescripcionTema());
        tema.setMateria(materia);

        Tema temaActualizado = temaRepository.save(tema);

        return toResponseDTO(temaActualizado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!temaRepository.existsById(id)) {
            throw new RuntimeException("Tema no encontrado con id: " + id);
        }

        temaRepository.deleteById(id);
    }

    private TemaResponseDTO toResponseDTO(Tema tema) {
        return new TemaResponseDTO(
                tema.getIdTema(),
                tema.getNombreTema(),
                tema.getDescripcionTema(),
                tema.getMateria().getIdMateria(),
                tema.getMateria().getNombreMateria()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemaResponseDTO> findByMateriaId(Long idMateria) {
        return temaRepository.findByMateria_IdMateria(idMateria)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
