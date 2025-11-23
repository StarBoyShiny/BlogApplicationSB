package com.orgPRO.BlogApplication.services;


import com.orgPRO.BlogApplication.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryServices {
    List<Category> listCategories();

    Category createCategory(Category entity);

    void deleteCategory(UUID id);

    Category getCategoryById(UUID id);
}
