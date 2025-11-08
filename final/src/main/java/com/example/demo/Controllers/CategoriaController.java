package com.example.demo.Controllers;

import com.example.demo.DTOs.CategoriaRequest;
import com.example.demo.DTOs.CategoriaResponse;
import com.example.demo.Services.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponse> listAll() {
        return categoriaService.findAll();
    }

    @GetMapping("/{id}")
    public CategoriaResponse getById(@PathVariable Long id) {
        return categoriaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse create(@Valid @RequestBody CategoriaRequest request) {
        return categoriaService.create(request);
    }

    @PutMapping("/{id}")
    public CategoriaResponse update(@PathVariable Long id, @Valid @RequestBody CategoriaRequest request) {
        return categoriaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoriaService.delete(id);
    }
}

