package com.example.demo.Controllers;

import com.example.demo.DTOs.CompraDetalleRequest;
import com.example.demo.DTOs.CompraDetalleResponse;
import com.example.demo.Services.CompraDetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras/detalles")
@RequiredArgsConstructor
public class CompraDetalleController {

    private final CompraDetalleService compraDetalleService;

    @GetMapping
    public List<CompraDetalleResponse> listAll() {
        return compraDetalleService.findAll();
    }

    @GetMapping("/{id}")
    public CompraDetalleResponse getById(@PathVariable Long id) {
        return compraDetalleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompraDetalleResponse create(@Valid @RequestBody CompraDetalleRequest request) {
        return compraDetalleService.create(request);
    }

    @PutMapping("/{id}")
    public CompraDetalleResponse update(@PathVariable Long id, @Valid @RequestBody CompraDetalleRequest request) {
        return compraDetalleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        compraDetalleService.delete(id);
    }
}

