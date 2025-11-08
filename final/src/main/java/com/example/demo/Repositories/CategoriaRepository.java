package com.example.demo.Repositories;

import com.example.demo.Entitites.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}

