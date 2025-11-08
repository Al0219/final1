package com.example.demo.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private String direccion;
    private Boolean activo;
}

