package com.example.demo.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDetalleRequest {

    @NotNull
    private Long compraId;

    @NotNull
    private Long productoId;

    @NotNull
    @Min(1)
    private Integer cantidad;

    @NotNull
    private BigDecimal costoUnitario;

    @NotNull
    private BigDecimal subtotal;

    @Builder.Default
    private Boolean activo = true;
}

