package org.example.proyecto_web.features.pregunta.repository;

import org.example.proyecto_web.core.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreguntaRepository extends JpaRepository <Pregunta, Long> {
    List<Pregunta> findByCuestionario_IdCuestionario(Long idCuestionario);
}
