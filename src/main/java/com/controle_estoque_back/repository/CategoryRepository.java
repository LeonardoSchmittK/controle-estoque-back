package com.controle_estoque_back.repository;

import com.controle_estoque_back.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
