package org.example.proyecto_web.features.recurso.repository;

import org.example.proyecto_web.core.entidades.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByTema_IdTema(Long idTema);
}