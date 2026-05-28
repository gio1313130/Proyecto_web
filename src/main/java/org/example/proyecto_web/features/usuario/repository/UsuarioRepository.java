package org.example.proyecto_web.features.usuario.repository;

import org.example.proyecto_web.core.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
