package com.example.demo.Controllers;

import com.example.demo.DTOs.ClienteRequest;
import com.example.demo.DTOs.ClienteResponse;
import com.example.demo.Services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponse> listAll() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ClienteResponse getById(@PathVariable Long id) {
        return clienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse create(@Valid @RequestBody ClienteRequest request) {
        return clienteService.create(request);
    }

    @PutMapping("/{id}")
    public ClienteResponse update(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return clienteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }
}

