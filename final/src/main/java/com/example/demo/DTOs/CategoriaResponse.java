package com.example.demo.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

