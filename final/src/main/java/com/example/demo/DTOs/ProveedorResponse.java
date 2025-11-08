package com.example.demo.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorResponse {

    private Long id;
    private String nombre;
    private String personaContacto;
    private String telefono;
    private String email;
    private String direccion;
    private Boolean activo;
}

