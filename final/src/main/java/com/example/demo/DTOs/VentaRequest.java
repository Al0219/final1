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
public class VentaRequest {

    private Long clienteId;

    private OffsetDateTime fecha;

    private String observacion;

    private BigDecimal total;

    @Builder.Default
    private Boolean activo = true;
}

