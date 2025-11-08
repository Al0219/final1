package com.example.demo.Services;

import com.example.demo.DTOs.VentaDetalleRequest;
import com.example.demo.DTOs.VentaDetalleResponse;
import com.example.demo.Entitites.Producto;
import com.example.demo.Entitites.Venta;
import com.example.demo.Entitites.VentaDetalle;
import com.example.demo.Repositories.ProductoRepository;
import com.example.demo.Repositories.VentaDetalleRepository;
import com.example.demo.Repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaDetalleService {

    private final VentaDetalleRepository ventaDetalleRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public List<VentaDetalleResponse> findAll() {
        return ventaDetalleRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public VentaDetalleResponse findById(Long id) {
        return ventaDetalleRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public VentaDetalleResponse create(VentaDetalleRequest request) {
        VentaDetalle detalle = new VentaDetalle();
        applyChanges(detalle, request);
        return toResponse(ventaDetalleRepository.save(detalle));
    }

    public VentaDetalleResponse update(Long id, VentaDetalleRequest request) {
        VentaDetalle detalle = ventaDetalleRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        applyChanges(detalle, request);
        return toResponse(ventaDetalleRepository.save(detalle));
    }

    public void delete(Long id) {
        VentaDetalle detalle = ventaDetalleRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        ventaDetalleRepository.delete(detalle);
    }

    private void applyChanges(VentaDetalle detalle, VentaDetalleRequest request) {
        Venta venta = ventaRepository.findById(request.getVentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Venta %d no existe".formatted(request.getVentaId())));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Producto %d no existe".formatted(request.getProductoId())));

        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        detalle.setDescuento(request.getDescuento());
        detalle.setSubtotal(request.getSubtotal());
        detalle.setActivo(activo);
    }

    private VentaDetalleResponse toResponse(VentaDetalle detalle) {
        return VentaDetalleResponse.builder()
                .id(detalle.getId())
                .ventaId(detalle.getVenta() != null ? detalle.getVenta().getId() : null)
                .productoId(detalle.getProducto() != null ? detalle.getProducto().getId() : null)
                .productoNombre(detalle.getProducto() != null ? detalle.getProducto().getNombre() : null)
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .descuento(detalle.getDescuento())
                .subtotal(detalle.getSubtotal())
                .activo(detalle.getActivo())
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "VentaDetalle %d no encontrado".formatted(id));
    }
}
