package com.ecommerce.ThymeleafEcommerce.service;

import com.ecommerce.ThymeleafEcommerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Optional<Category> getById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);

    List<Category> findByActivatedTrue();
}
