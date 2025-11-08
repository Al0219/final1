package com.example.demo.Repositories;

import com.example.demo.Entitites.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

