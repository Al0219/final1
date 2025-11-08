package com.example.demo.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequest {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    private String telefono;

    private String email;

    private String direccion;

    @Builder.Default
    private Boolean activo = true;
}

