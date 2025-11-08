package com.example.demo.Services;

import com.example.demo.DTOs.ClienteRequest;
import com.example.demo.DTOs.ClienteResponse;
import com.example.demo.Entitites.Cliente;
import com.example.demo.Repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClienteResponse findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public ClienteResponse create(ClienteRequest request) {
        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        Cliente cliente = Cliente.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .activo(activo)
                .build();

        return toResponse(clienteRepository.save(cliente));
    }

    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> notFound(id));

        cliente.setNombres(request.getNombres());
        cliente.setApellidos(request.getApellidos());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());
        cliente.setDireccion(request.getDireccion());
        if (request.getActivo() != null) {
            cliente.setActivo(request.getActivo());
        }

        return toResponse(clienteRepository.save(cliente));
    }

    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        clienteRepository.delete(cliente);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .direccion(cliente.getDireccion())
                .activo(cliente.getActivo())
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente %d no encontrado".formatted(id));
    }
}

