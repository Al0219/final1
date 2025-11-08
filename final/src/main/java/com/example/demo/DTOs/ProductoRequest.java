package com.example.demo.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @PositiveOrZero
    private BigDecimal precioCompraDef;

    @NotNull
    @PositiveOrZero
    private BigDecimal precioVenta;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    @NotNull
    private Long categoriaId;

    private Long proveedorId;

    @Builder.Default
    private Boolean activo = true;
}

