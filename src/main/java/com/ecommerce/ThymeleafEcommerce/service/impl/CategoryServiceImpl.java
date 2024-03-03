package com.ecommerce.ThymeleafEcommerce.service.impl;

import com.ecommerce.ThymeleafEcommerce.model.Category;
import com.ecommerce.ThymeleafEcommerce.repository.CategoryRepository;
import com.ecommerce.ThymeleafEcommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repository;
    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        //Category categorySave = new Category();
        //categorySave.setName(category.getName());
        //categorySave.setDeleted(false);
        category.setActivated(true);
        return repository.save(category);
    }

    @Override
    public Optional<Category> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = repository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return repository.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = repository.findById(id).get();
        category.setActivated(false);
        category.setDeleted(true);
        repository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = repository.findById(id).get();
        category.setActivated(true);
        category.setDeleted(false);
        repository.save(category);
    }

    @Override
    public List<Category> findByActivatedTrue() {
        return repository.findByActivatedTrue();
    }
}
