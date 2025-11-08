package com.example.demo.Controllers;

import com.example.demo.DTOs.ProductoRequest;
import com.example.demo.DTOs.ProductoResponse;
import com.example.demo.Services.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> listAll() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ProductoResponse getById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse create(@Valid @RequestBody ProductoRequest request) {
        return productoService.create(request);
    }

    @PutMapping("/{id}")
    public ProductoResponse update(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return productoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productoService.delete(id);
    }
}

