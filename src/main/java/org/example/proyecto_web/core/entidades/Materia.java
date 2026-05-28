package org.example.proyecto_web.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "materia")
public class Materia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia", nullable = false)
    private Long idMateria;

    @Column(name = "nombre_materia",length = 50, nullable = false)
    private String nombreMateria;

    @Column(name = "semestre", nullable = false)
    private Integer semestre;

    @Column(name = "descripcion_materia",length = 250, nullable = true)
    private String descripcionMateria;


    @OneToMany(
            mappedBy = "materia",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Tema> temas = new HashSet<>();

}

