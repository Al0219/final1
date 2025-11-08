package com.example.demo.Services;

import com.example.demo.DTOs.CompraDetalleRequest;
import com.example.demo.DTOs.CompraDetalleResponse;
import com.example.demo.Entitites.Compra;
import com.example.demo.Entitites.CompraDetalle;
import com.example.demo.Entitites.Producto;
import com.example.demo.Repositories.CompraDetalleRepository;
import com.example.demo.Repositories.CompraRepository;
import com.example.demo.Repositories.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraDetalleService {

    private final CompraDetalleRepository compraDetalleRepository;
    private final CompraRepository compraRepository;
    private final ProductoRepository productoRepository;

    public List<CompraDetalleResponse> findAll() {
        return compraDetalleRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public CompraDetalleResponse findById(Long id) {
        return compraDetalleRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public CompraDetalleResponse create(CompraDetalleRequest request) {
        CompraDetalle detalle = new CompraDetalle();
        applyChanges(detalle, request);
        return toResponse(compraDetalleRepository.save(detalle));
    }

    public CompraDetalleResponse update(Long id, CompraDetalleRequest request) {
        CompraDetalle detalle = compraDetalleRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        applyChanges(detalle, request);
        return toResponse(compraDetalleRepository.save(detalle));
    }

    public void delete(Long id) {
        CompraDetalle detalle = compraDetalleRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        compraDetalleRepository.delete(detalle);
    }

    private void applyChanges(CompraDetalle detalle, CompraDetalleRequest request) {
        Compra compra = compraRepository.findById(request.getCompraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Compra %d no existe".formatted(request.getCompraId())));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Producto %d no existe".formatted(request.getProductoId())));

        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        detalle.setCompra(compra);
        detalle.setProducto(producto);
        detalle.setCantidad(request.getCantidad());
        detalle.setCostoUnitario(request.getCostoUnitario());
        detalle.setSubtotal(request.getSubtotal());
        detalle.setActivo(activo);
    }

    private CompraDetalleResponse toResponse(CompraDetalle detalle) {
        return CompraDetalleResponse.builder()
                .id(detalle.getId())
                .compraId(detalle.getCompra() != null ? detalle.getCompra().getId() : null)
                .productoId(detalle.getProducto() != null ? detalle.getProducto().getId() : null)
                .productoNombre(detalle.getProducto() != null ? detalle.getProducto().getNombre() : null)
                .cantidad(detalle.getCantidad())
                .costoUnitario(detalle.getCostoUnitario())
                .subtotal(detalle.getSubtotal())
                .activo(detalle.getActivo())
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "CompraDetalle %d no encontrado".formatted(id));
    }
}

