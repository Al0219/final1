package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "producto_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nombre;

    @Column(length = 250)
    private String descripcion;

    @Column(name = "precio_compra_def", precision = 12, scale = 2)
    private BigDecimal precioCompraDef;

    @Column(name = "precio_venta", precision = 12, scale = 2, nullable = false)
    private BigDecimal precioVenta;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @Column(name = "actualizado_en", nullable = false, insertable = false, updatable = false)
    private LocalDateTime actualizadoEn;
}
