package com.example.demo.Services;

import com.example.demo.DTOs.ProductoRequest;
import com.example.demo.DTOs.ProductoResponse;
import com.example.demo.Entitites.Categoria;
import com.example.demo.Entitites.Producto;
import com.example.demo.Entitites.Proveedor;
import com.example.demo.Repositories.CategoriaRepository;
import com.example.demo.Repositories.ProductoRepository;
import com.example.demo.Repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProveedorRepository proveedorRepository;

    public List<ProductoResponse> findAll() {
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductoResponse findById(Long id) {
        return productoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> notFound(id));
    }

    public ProductoResponse create(ProductoRequest request) {
        Producto producto = new Producto();
        applyChanges(producto, request);
        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse update(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        applyChanges(producto, request);
        return toResponse(productoRepository.save(producto));
    }

    public void delete(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> notFound(id));
        productoRepository.delete(producto);
    }

    private void applyChanges(Producto producto, ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Categoria %d no existe".formatted(request.getCategoriaId())));

        Proveedor proveedor = null;
        if (request.getProveedorId() != null) {
            proveedor = proveedorRepository.findById(request.getProveedorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Proveedor %d no existe".formatted(request.getProveedorId())));
        }

        Boolean activo = request.getActivo() == null ? Boolean.TRUE : request.getActivo();

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecioCompraDef(request.getPrecioCompraDef());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);
        producto.setActivo(activo);
    }

    private ProductoResponse toResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precioCompraDef(producto.getPrecioCompraDef())
                .precioVenta(producto.getPrecioVenta())
                .stock(producto.getStock())
                .activo(producto.getActivo())
                .actualizadoEn(producto.getActualizadoEn())
                .categoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null)
                .categoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
                .proveedorId(producto.getProveedor() != null ? producto.getProveedor().getId() : null)
                .proveedorNombre(producto.getProveedor() != null ? producto.getProveedor().getNombre() : null)
                .build();
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto %d no encontrado".formatted(id));
    }
}

