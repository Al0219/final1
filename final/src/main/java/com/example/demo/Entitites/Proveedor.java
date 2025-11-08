package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proveedor_seq")
    @SequenceGenerator(name = "proveedor_seq", sequenceName = "proveedor_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nombre;

    @Column(name = "persona_contacto", length = 150)
    private String personaContacto;

    @Column(length = 30)
    private String telefono;

    @Column(length = 150)
    private String email;

    @Column(length = 200)
    private String direccion;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
