package org.example.proyecto_web.features.opcion.repository;

import org.example.proyecto_web.core.entidades.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcionRepository extends JpaRepository<Opcion,Long> {
    boolean existsByPregunta_IdPreguntaAndEsCorrectaTrue(Long idPregunta);

    boolean existsByPregunta_IdPreguntaAndEsCorrectaTrueAndIdOpcionNot(Long idPregunta, Long idOpcion);
    List<Opcion> findByPregunta_IdPregunta(Long idPregunta);
}
