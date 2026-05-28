package org.example.proyecto_web.features.materia.service.impl;

import org.example.proyecto_web.core.entidades.Materia;
import org.example.proyecto_web.features.materia.dto.MateriaRequestDTO;
import org.example.proyecto_web.features.materia.dto.MateriaResponseDTO;
import org.example.proyecto_web.features.materia.repository.MateriaRepository;
import org.example.proyecto_web.features.materia.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;


    @Override
    @Transactional(readOnly = true)
    public List<MateriaResponseDTO> findAll() {
        return materiaRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public MateriaResponseDTO findById(Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));

        return toResponseDTO(materia);
    }

    @Override
    @Transactional
    public MateriaResponseDTO save(MateriaRequestDTO materiaRequestDTO) {
        Materia materia = new Materia();

        materia.setNombreMateria(materiaRequestDTO.getNombreMateria());
        materia.setSemestre(materiaRequestDTO.getSemestre());
        materia.setDescripcionMateria(materiaRequestDTO.getDescripcionMateria());

        Materia materiaGuardada = materiaRepository.save(materia);

        return toResponseDTO(materiaGuardada);
    }

    @Override
    @Transactional
    public MateriaResponseDTO update(Long id, MateriaRequestDTO materiaRequestDTO) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con id: " + id));

        materia.setNombreMateria(materiaRequestDTO.getNombreMateria());
        materia.setSemestre(materiaRequestDTO.getSemestre());
        materia.setDescripcionMateria(materiaRequestDTO.getDescripcionMateria());

        Materia materiaActualizada = materiaRepository.save(materia);

        return toResponseDTO(materiaActualizada);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new RuntimeException("Materia no encontrada con id: " + id);
        }

        materiaRepository.deleteById(id);
    }

    private MateriaResponseDTO toResponseDTO(Materia materia) {
        return new MateriaResponseDTO(
                materia.getIdMateria(),
                materia.getNombreMateria(),
                materia.getSemestre(),
                materia.getDescripcionMateria()
        );
    }
}