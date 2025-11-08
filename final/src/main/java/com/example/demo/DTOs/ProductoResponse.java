package com.example.demo.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precioCompraDef;
    private BigDecimal precioVenta;
    private Integer stock;
    private Boolean activo;
    private LocalDateTime actualizadoEn;

    private Long categoriaId;
    private String categoriaNombre;

    private Long proveedorId;
    private String proveedorNombre;
}

