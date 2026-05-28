package org.example.proyecto_web.core.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opcion")
public class Opcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opcion", nullable = false)
    private Long idOpcion;

    @Column(name = "texto_opcion", length = 250, nullable = false)
    private String textoOpcion;

    @Column(name = "es_correcta", nullable = false)
    private Boolean esCorrecta;

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    private Pregunta pregunta;

}

