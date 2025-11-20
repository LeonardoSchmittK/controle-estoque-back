package com.controle_estoque_back.repository;

import com.controle_estoque_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

