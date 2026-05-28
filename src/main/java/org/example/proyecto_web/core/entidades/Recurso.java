package org.example.proyecto_web.core.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recurso")
public class Recurso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso", nullable = false)
    private Long idRecurso;

    @Column(name = "titulo_recurso", length = 100,nullable = false)
    private String tituloRecurso;

    @Column(name = "tipo_recurso", length = 100,nullable = false)
    private String tipoRecurso;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private LocalDateTime fechaPublicacion;

    @PrePersist
    public void prePersist() {
        this.fechaPublicacion = LocalDateTime.now();
    }

    @Column(name = "autor", length = 50, nullable = false)
    private String autor;

    @Column(name = "descripcion_recurso",length = 250, nullable = true)
    private String descripcionRecurso;

    @Column(name = "url", length = 1000,nullable = false)
    private String url;

    @Column(name = "public_id", length = 255, nullable = false)
    private String publicId;

    @Column(name = "resource_type", length = 50, nullable = false)
    private String resourceType;

    @ManyToOne
    @JoinColumn(name = "id_tema", nullable = false)
    private Tema tema;

}

