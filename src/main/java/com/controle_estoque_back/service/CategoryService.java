package com.controle_estoque_back.service;

import com.controle_estoque_back.entity.Category;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final Map<Long, Category> database = new HashMap<>();
    private long counter = 1;

    public List<Category> findAll() {
        return new ArrayList<>(database.values());
    }

    public Category findById(Long id) {
        return database.get(id);
    }

    public Category save(Category category) {
        category.setId(counter++);
        database.put(category.getId(), category);
        return category;
    }

    public Category update(Long id, Category updated) {
        Category existing = database.get(id);
        if (existing == null) return null;

        existing.setName(updated.getName());
        existing.setSize(updated.getSize());
        existing.setPackaging(updated.getPackaging());
        return existing;
    }

    public void delete(Long id) {
        database.remove(id);
    }
}
