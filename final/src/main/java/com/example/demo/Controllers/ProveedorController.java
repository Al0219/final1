package com.example.demo.Controllers;

import com.example.demo.DTOs.ProveedorRequest;
import com.example.demo.DTOs.ProveedorResponse;
import com.example.demo.Services.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorResponse> listAll() {
        return proveedorService.findAll();
    }

    @GetMapping("/{id}")
    public ProveedorResponse getById(@PathVariable Long id) {
        return proveedorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorResponse create(@Valid @RequestBody ProveedorRequest request) {
        return proveedorService.create(request);
    }

    @PutMapping("/{id}")
    public ProveedorResponse update(@PathVariable Long id, @Valid @RequestBody ProveedorRequest request) {
        return proveedorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        proveedorService.delete(id);
    }
}

