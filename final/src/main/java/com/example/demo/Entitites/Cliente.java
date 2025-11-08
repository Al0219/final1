package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 120, nullable = false)
    private String nombres;

    @Column(length = 120, nullable = false)
    private String apellidos;

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
