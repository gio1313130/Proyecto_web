package org.example.proyecto_web.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tema")
public class Tema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tema",nullable = false)
    private Long idTema;

    @Column(name = "nombre_tema",length = 100, nullable = false)
    private String nombreTema;

    @Column(name = "descripcion_tema", length = 250 , nullable = true)
    private String descripcionTema;

    @ManyToOne
    @JoinColumn(name = "id_materia", nullable = false)
    private Materia materia;

    @OneToMany(
            mappedBy = "tema",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Cuestionario> cuestionarios = new HashSet<>();

    @OneToMany(
            mappedBy = "tema",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Recurso> recursos = new HashSet<>();

}
