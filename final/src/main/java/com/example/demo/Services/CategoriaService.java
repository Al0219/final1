package com.example.demo.Services;

import com.example.demo.DTOs.CategoriaRequest;
import com.example.demo.DTOs.CategoriaResponse;
import com.example.demo.Entitites.Categoria;
import com.example.demo.Repositories.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<CategoriaResponse> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoriaResponse findById(Long id) {
        return categoriaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public CategoriaResponse create(CategoriaRequest request) {
        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        Categoria categoria = Categoria.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(activo)
                .build();

        return toResponse(categoriaRepository.save(categoria));
    }

    public CategoriaResponse update(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> notFound(id));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        if (request.getActivo() != null) {
            categoria.setActivo(request.getActivo());
        }

        return toResponse(categoriaRepository.save(categoria));
    }

    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        categoriaRepository.delete(categoria);
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .activo(categoria.getActivo())
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria %d no encontrada".formatted(id));
    }
}
