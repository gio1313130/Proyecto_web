package org.example.proyecto_web.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuestionario")
public class Cuestionario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuestionario",nullable = false)
    private Long idCuestionario;

    @Column(name = "titulo_cuestionario", length = 50 , nullable = false)
    private String tituloCuestionario;

    @Column(name = "dificultad", length = 20, nullable = false)
    private String dificultad;

    @ManyToOne
    @JoinColumn(name = "id_tema", nullable = false)
    private Tema tema;

    @OneToMany(
            mappedBy = "cuestionario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Intento> intentos = new HashSet<>();

    @OneToMany(
            mappedBy = "cuestionario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<Pregunta> preguntas = new HashSet<>();

    
}
