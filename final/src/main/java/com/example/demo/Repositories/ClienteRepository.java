package com.example.demo.Repositories;

import com.example.demo.Entitites.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

