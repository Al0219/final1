package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "venta_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venta_detalle_seq")
    @SequenceGenerator(name = "venta_detalle_seq", sequenceName = "venta_detalle_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal descuento;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
