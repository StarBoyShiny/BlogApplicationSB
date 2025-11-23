package com.orgPRO.BlogApplication.services.impl;

import com.orgPRO.BlogApplication.domain.entities.Category;
import com.orgPRO.BlogApplication.repositories.CategoryRepository;
import com.orgPRO.BlogApplication.services.CategoryServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryServices {
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty()) {
                throw new IllegalStateException("Category has posts associated with it");
            }
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Category getCategoryById(UUID id) {
       return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found" + id));
    }

    @Transactional
    @Override
    public Category createCategory(Category entity) {
        String entityName = entity.getName();
        if (categoryRepository.existsByNameIgnoreCase(entityName)) {
            throw new IllegalArgumentException("Category already exists with name");
        }
        return categoryRepository.save(entity);
    }


    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }
}
