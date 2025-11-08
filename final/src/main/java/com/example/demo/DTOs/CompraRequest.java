package com.example.demo.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraRequest {

    @NotNull
    private Long proveedorId;

    private OffsetDateTime fecha;

    private String observacion;

    private BigDecimal total;

    @Builder.Default
    private Boolean activo = true;
}

