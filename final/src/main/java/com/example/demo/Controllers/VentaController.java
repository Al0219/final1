package com.example.demo.Controllers;

import com.example.demo.DTOs.VentaRequest;
import com.example.demo.DTOs.VentaResponse;
import com.example.demo.Services.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public List<VentaResponse> listAll() {
        return ventaService.findAll();
    }

    @GetMapping("/{id}")
    public VentaResponse getById(@PathVariable Long id) {
        return ventaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaResponse create(@Valid @RequestBody VentaRequest request) {
        return ventaService.create(request);
    }

    @PutMapping("/{id}")
    public VentaResponse update(@PathVariable Long id, @Valid @RequestBody VentaRequest request) {
        return ventaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ventaService.delete(id);
    }
}
