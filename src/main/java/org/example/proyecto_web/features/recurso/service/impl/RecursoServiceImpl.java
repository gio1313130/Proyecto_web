package org.example.proyecto_web.features.recurso.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.example.proyecto_web.core.entidades.Recurso;
import org.example.proyecto_web.core.entidades.Tema;
import org.example.proyecto_web.features.recurso.dto.RecursoRequestDTO;
import org.example.proyecto_web.features.recurso.dto.RecursoResponseDTO;
import org.example.proyecto_web.features.recurso.repository.RecursoRepository;
import org.example.proyecto_web.features.recurso.service.RecursoService;
import org.example.proyecto_web.features.tema.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private TemaRepository temaRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    @Transactional(readOnly = true)
    public List<RecursoResponseDTO> findAll() {
        return recursoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecursoResponseDTO findById(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + id));

        return toResponseDTO(recurso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecursoResponseDTO> findByTemaId(Long idTema) {
        return recursoRepository.findByTema_IdTema(idTema)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public RecursoResponseDTO upload(
            MultipartFile file,
            String tituloRecurso,
            String tipoRecurso,
            String autor,
            String descripcionRecurso,
            Long idTema
    ) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("El archivo no puede estar vacío");
        }

        Tema tema = temaRepository.findById(idTema)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + idTema));

        try {
            String resourceType = determinarResourceType(tipoRecurso);

            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.isBlank()) {
                originalFilename = "archivo";
            }

            String extension = "";
            int lastDot = originalFilename.lastIndexOf(".");
            if (lastDot != -1) {
                extension = originalFilename.substring(lastDot);
            }

            String baseName = originalFilename.replace(extension, "")
                    .replaceAll("[^a-zA-Z0-9_-]", "_");

            String publicId;

            if ("raw".equals(resourceType)) {
                publicId = "recursos/" + baseName + "_" + System.currentTimeMillis() + extension;
            } else {
                publicId = "recursos/" + baseName + "_" + System.currentTimeMillis();
            }

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", resourceType,
                            "public_id", publicId
                    )
            );

            String url = uploadResult.get("secure_url").toString();
            String publicIdGuardado = uploadResult.get("public_id").toString();
            String cloudinaryResourceType = uploadResult.get("resource_type").toString();

            Recurso recurso = new Recurso();
            recurso.setTituloRecurso(tituloRecurso);
            recurso.setTipoRecurso(tipoRecurso);
            recurso.setAutor(autor);
            recurso.setDescripcionRecurso(descripcionRecurso);
            recurso.setUrl(url);
            recurso.setPublicId(publicIdGuardado);
            recurso.setResourceType(cloudinaryResourceType);
            recurso.setTema(tema);

            Recurso recursoGuardado = recursoRepository.save(recurso);

            return toResponseDTO(recursoGuardado);

        } catch (Exception e) {
            throw new RuntimeException("Error al subir archivo a Cloudinary: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + id));

        try {
            cloudinary.uploader().destroy(
                    recurso.getPublicId(),
                    ObjectUtils.asMap(
                            "resource_type", recurso.getResourceType()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar archivo de Cloudinary: " + e.getMessage());
        }

        recursoRepository.deleteById(id);
    }

    private String determinarResourceType(String tipoRecurso) {
        if (tipoRecurso == null || tipoRecurso.isBlank()) {
            throw new RuntimeException("El tipo de recurso es obligatorio");
        }

        return switch (tipoRecurso.toUpperCase()) {
            case "VIDEO" -> "video";
            case "IMAGEN", "IMAGE" -> "image";
            case "PDF", "DOCUMENTO", "DOC", "RAW" -> "raw";
            default -> throw new RuntimeException("Tipo de recurso no válido: " + tipoRecurso);
        };
    }

    private RecursoResponseDTO toResponseDTO(Recurso recurso) {
        return new RecursoResponseDTO(
                recurso.getIdRecurso(),
                recurso.getTituloRecurso(),
                recurso.getTipoRecurso(),
                recurso.getFechaPublicacion(),
                recurso.getAutor(),
                recurso.getDescripcionRecurso(),
                recurso.getUrl(),
                recurso.getPublicId(),
                recurso.getResourceType(),
                recurso.getTema().getIdTema(),
                recurso.getTema().getNombreTema()
        );
    }

    @Override
    @Transactional
    public RecursoResponseDTO update(Long id, RecursoRequestDTO recursoRequestDTO) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con id: " + id));

        Tema tema = temaRepository.findById(recursoRequestDTO.getIdTema())
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con id: " + recursoRequestDTO.getIdTema()));

        recurso.setTituloRecurso(recursoRequestDTO.getTituloRecurso());
        recurso.setTipoRecurso(recursoRequestDTO.getTipoRecurso());
        recurso.setAutor(recursoRequestDTO.getAutor());
        recurso.setDescripcionRecurso(recursoRequestDTO.getDescripcionRecurso());
        recurso.setTema(tema);

        Recurso recursoActualizado = recursoRepository.save(recurso);

        return toResponseDTO(recursoActualizado);
    }
}