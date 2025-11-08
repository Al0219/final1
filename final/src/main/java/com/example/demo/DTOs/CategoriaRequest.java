package com.example.demo.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaRequest {

    @NotBlank
    private String nombre;

    private String descripcion;

    @Builder.Default
    private Boolean activo = true;
}
