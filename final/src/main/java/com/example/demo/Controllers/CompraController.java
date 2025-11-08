package com.example.demo.Controllers;

import com.example.demo.DTOs.CompraRequest;
import com.example.demo.DTOs.CompraResponse;
import com.example.demo.Services.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public List<CompraResponse> listAll() {
        return compraService.findAll();
    }

    @GetMapping("/{id}")
    public CompraResponse getById(@PathVariable Long id) {
        return compraService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompraResponse create(@Valid @RequestBody CompraRequest request) {
        return compraService.create(request);
    }

    @PutMapping("/{id}")
    public CompraResponse update(@PathVariable Long id, @Valid @RequestBody CompraRequest request) {
        return compraService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        compraService.delete(id);
    }
}

