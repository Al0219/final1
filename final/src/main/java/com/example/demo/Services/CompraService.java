package com.example.demo.Services;

import com.example.demo.DTOs.CompraRequest;
import com.example.demo.DTOs.CompraResponse;
import com.example.demo.Entitites.Compra;
import com.example.demo.Entitites.Proveedor;
import com.example.demo.Repositories.CompraRepository;
import com.example.demo.Repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;

    public List<CompraResponse> findAll() {
        return compraRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CompraResponse findById(Long id) {
        return compraRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public CompraResponse create(CompraRequest request) {
        Compra compra = new Compra();
        applyChanges(compra, request);
        return toResponse(compraRepository.save(compra));
    }

    public CompraResponse update(Long id, CompraRequest request) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        applyChanges(compra, request);
        return toResponse(compraRepository.save(compra));
    }

    public void delete(Long id) {
        Compra compra = compraRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        compraRepository.delete(compra);
    }

    private void applyChanges(Compra compra, CompraRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Proveedor %d no existe".formatted(request.getProveedorId())));

        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();
        OffsetDateTime fecha = request.getFecha() != null ? request.getFecha() : OffsetDateTime.now();
        BigDecimal total = request.getTotal() != null ? request.getTotal() : BigDecimal.ZERO;

        compra.setProveedor(proveedor);
        compra.setFecha(fecha);
        compra.setObservacion(request.getObservacion());
        compra.setTotal(total);
        compra.setActivo(activo);
    }

    private CompraResponse toResponse(Compra compra) {
        return CompraResponse.builder()
                .id(compra.getId())
                .fecha(compra.getFecha())
                .observacion(compra.getObservacion())
                .total(compra.getTotal())
                .activo(compra.getActivo())
                .proveedorId(compra.getProveedor() != null ? compra.getProveedor().getId() : null)
                .proveedorNombre(compra.getProveedor() != null ? compra.getProveedor().getNombre() : null)
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Compra %d no encontrada".formatted(id));
    }
}

