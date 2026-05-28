package org.example.proyecto_web.core.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pregunta")
public class Pregunta  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pregunta",nullable = false)
    private Long idPregunta;

    @Column(name = "enunciado",length = 500, nullable = false)
    private String enunciado;

    @ManyToOne
    @JoinColumn(name = "id_cuestionario", nullable = false)
    private Cuestionario cuestionario;

    @OneToMany(
            mappedBy = "pregunta",
            cascade = CascadeType.ALL,
            orphanRemoval = true    
    )
    @JsonIgnore
    private Set<Opcion> opciones = new HashSet<>();

}
