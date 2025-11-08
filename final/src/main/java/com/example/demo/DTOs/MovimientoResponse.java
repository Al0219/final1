package com.example.demo.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoResponse {

    private String tipo; // COMPRA o VENTA
    private Long id;
    private OffsetDateTime fecha;
    private String observacion;
    private BigDecimal total;
    private Boolean activo;

    private Long contraparteId; // proveedor o cliente
    private String contraparteNombre;
}
