package com.example.demo.Services;

import com.example.demo.DTOs.VentaRequest;
import com.example.demo.DTOs.VentaResponse;
import com.example.demo.Entitites.Cliente;
import com.example.demo.Entitites.Venta;
import com.example.demo.Repositories.ClienteRepository;
import com.example.demo.Repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public List<VentaResponse> findAll() {
        return ventaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public VentaResponse findById(Long id) {
        return ventaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public VentaResponse create(VentaRequest request) {
        Venta venta = new Venta();
        applyChanges(venta, request);
        return toResponse(ventaRepository.save(venta));
    }

    public VentaResponse update(Long id, VentaRequest request) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        applyChanges(venta, request);
        return toResponse(ventaRepository.save(venta));
    }

    public void delete(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        ventaRepository.delete(venta);
    }

    private void applyChanges(Venta venta, VentaRequest request) {
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Cliente %d no existe".formatted(request.getClienteId())));
        }

        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();
        OffsetDateTime fecha = request.getFecha() != null ? request.getFecha() : OffsetDateTime.now();
        BigDecimal total = request.getTotal() != null ? request.getTotal() : BigDecimal.ZERO;

        venta.setCliente(cliente);
        venta.setFecha(fecha);
        venta.setObservacion(request.getObservacion());
        venta.setTotal(total);
        venta.setActivo(activo);
    }

    private VentaResponse toResponse(Venta venta) {
        return VentaResponse.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .observacion(venta.getObservacion())
                .total(venta.getTotal())
                .activo(venta.getActivo())
                .clienteId(venta.getCliente() != null ? venta.getCliente().getId() : null)
                .clienteNombre(venta.getCliente() != null ? venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos() : null)
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Venta %d no encontrada".formatted(id));
    }
}
