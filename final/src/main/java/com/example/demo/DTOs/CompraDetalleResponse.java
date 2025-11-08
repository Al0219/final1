package com.example.demo.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDetalleResponse {

    private Long id;
    private Long compraId;
    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal subtotal;
    private Boolean activo;
}

