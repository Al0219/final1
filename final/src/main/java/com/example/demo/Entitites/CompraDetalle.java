package com.example.demo.Entitites;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "compra_detalle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compra_detalle_seq")
    @SequenceGenerator(name = "compra_detalle_seq", sequenceName = "compra_detalle_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "costo_unitario", precision = 12, scale = 2, nullable = false)
    private BigDecimal costoUnitario;

    @Column(precision = 14, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}
