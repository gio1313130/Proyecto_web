package org.example.proyecto_web.features.intento.repository;

import org.example.proyecto_web.core.entidades.Intento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntentoRepository extends JpaRepository <Intento, Long> {
    List<Intento> findByUsuario_IdUsuario(Long idUsuario);
}
