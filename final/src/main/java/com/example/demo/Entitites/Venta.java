package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_seq")
    @SequenceGenerator(name = "venta_seq", sequenceName = "venta_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(nullable = false)
    private OffsetDateTime fecha;

    @Column(length = 250)
    private String observacion;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
