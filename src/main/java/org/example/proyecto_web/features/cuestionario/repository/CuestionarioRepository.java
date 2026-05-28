package org.example.proyecto_web.features.cuestionario.repository;

import org.example.proyecto_web.core.entidades.Cuestionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuestionarioRepository extends JpaRepository<Cuestionario, Long> {
    List<Cuestionario> findByTema_IdTema(Long idTema);
}
