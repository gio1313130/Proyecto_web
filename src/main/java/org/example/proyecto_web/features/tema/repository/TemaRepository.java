package org.example.proyecto_web.features.tema.repository;

import org.example.proyecto_web.core.entidades.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemaRepository extends JpaRepository <Tema, Long> {
    List<Tema> findByMateria_IdMateria(Long idMateria);
}
