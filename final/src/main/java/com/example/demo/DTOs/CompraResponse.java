package com.example.demo.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponse {

    private Long id;
    private OffsetDateTime fecha;
    private String observacion;
    private BigDecimal total;
    private Boolean activo;

    private Long proveedorId;
    private String proveedorNombre;
}

