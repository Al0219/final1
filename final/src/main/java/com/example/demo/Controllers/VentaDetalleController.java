package com.example.demo.Controllers;

import com.example.demo.DTOs.VentaDetalleRequest;
import com.example.demo.DTOs.VentaDetalleResponse;
import com.example.demo.Services.VentaDetalleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas/detalles")
@RequiredArgsConstructor
public class VentaDetalleController {

    private final VentaDetalleService ventaDetalleService;

    @GetMapping
    public List<VentaDetalleResponse> listAll() {
        return ventaDetalleService.findAll();
    }

    @GetMapping("/{id}")
    public VentaDetalleResponse getById(@PathVariable Long id) {
        return ventaDetalleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VentaDetalleResponse create(@Valid @RequestBody VentaDetalleRequest request) {
        return ventaDetalleService.create(request);
    }

    @PutMapping("/{id}")
    public VentaDetalleResponse update(@PathVariable Long id, @Valid @RequestBody VentaDetalleRequest request) {
        return ventaDetalleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ventaDetalleService.delete(id);
    }
}
