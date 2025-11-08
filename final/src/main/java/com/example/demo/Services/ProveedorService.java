package com.example.demo.Services;

import com.example.demo.DTOs.ProveedorRequest;
import com.example.demo.DTOs.ProveedorResponse;
import com.example.demo.Entitites.Proveedor;
import com.example.demo.Repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<ProveedorResponse> findAll() {
        return proveedorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProveedorResponse findById(Long id) {
        return proveedorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public ProveedorResponse create(ProveedorRequest request) {
        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        Proveedor proveedor = Proveedor.builder()
                .nombre(request.getNombre())
                .personaContacto(request.getPersonaContacto())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .activo(activo)
                .build();

        return toResponse(proveedorRepository.save(proveedor));
    }

    public ProveedorResponse update(Long id, ProveedorRequest request) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> notFound(id));

        proveedor.setNombre(request.getNombre());
        proveedor.setPersonaContacto(request.getPersonaContacto());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setEmail(request.getEmail());
        proveedor.setDireccion(request.getDireccion());
        if (request.getActivo() != null) {
            proveedor.setActivo(request.getActivo());
        }

        return toResponse(proveedorRepository.save(proveedor));
    }

    public void delete(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        proveedorRepository.delete(proveedor);
    }

    private ProveedorResponse toResponse(Proveedor proveedor) {
        return ProveedorResponse.builder()
                .id(proveedor.getId())
                .nombre(proveedor.getNombre())
                .personaContacto(proveedor.getPersonaContacto())
                .telefono(proveedor.getTelefono())
                .email(proveedor.getEmail())
                .direccion(proveedor.getDireccion())
                .activo(proveedor.getActivo())
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor %d no encontrado".formatted(id));
    }
}

