package com.example.demo.Services;

import com.example.demo.DTOs.MovimientoResponse;
import com.example.demo.Entitites.Compra;
import com.example.demo.Entitites.Venta;
import com.example.demo.Repositories.CompraRepository;
import com.example.demo.Repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final CompraRepository compraRepository;
    private final VentaRepository ventaRepository;

    public List<MovimientoResponse> listMovimientos(OffsetDateTime from, OffsetDateTime to) {
        Stream<MovimientoResponse> compras = compraRepository.findAll().stream()
                .map(this::fromCompra);

        Stream<MovimientoResponse> ventas = ventaRepository.findAll().stream()
                .map(this::fromVenta);

        return Stream.concat(compras, ventas)
                .filter(mov -> from == null || (mov.getFecha() != null && !mov.getFecha().isBefore(from)))
                .filter(mov -> to == null || (mov.getFecha() != null && !mov.getFecha().isAfter(to)))
                .sorted(Comparator.comparing(MovimientoResponse::getFecha, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();
    }

    private MovimientoResponse fromCompra(Compra compra) {
        return MovimientoResponse.builder()
                .tipo("COMPRA")
                .id(compra.getId())
                .fecha(compra.getFecha())
                .observacion(compra.getObservacion())
                .total(compra.getTotal())
                .activo(compra.getActivo())
                .contraparteId(compra.getProveedor() != null ? compra.getProveedor().getId() : null)
                .contraparteNombre(compra.getProveedor() != null ? compra.getProveedor().getNombre() : null)
                .build();
    }

    private MovimientoResponse fromVenta(Venta venta) {
        String clienteNombre = null;
        if (venta.getCliente() != null) {
            String nombres = venta.getCliente().getNombres() != null ? venta.getCliente().getNombres() : "";
            String apellidos = venta.getCliente().getApellidos() != null ? venta.getCliente().getApellidos() : "";
            clienteNombre = (nombres + " " + apellidos).trim();
            if (clienteNombre.isEmpty()) {
                clienteNombre = null;
            }
        }

        return MovimientoResponse.builder()
                .tipo("VENTA")
                .id(venta.getId())
                .fecha(venta.getFecha())
                .observacion(venta.getObservacion())
                .total(venta.getTotal())
                .activo(venta.getActivo())
                .contraparteId(venta.getCliente() != null ? venta.getCliente().getId() : null)
                .contraparteNombre(clienteNombre)
                .build();
    }
}
