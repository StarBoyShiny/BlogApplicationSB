package com.orgPRO.BlogApplication.controllers;

import com.orgPRO.BlogApplication.domain.dtos.CategoryDto;
import com.orgPRO.BlogApplication.domain.dtos.CreateCategoryRequest;
import com.orgPRO.BlogApplication.domain.entities.Category;
import com.orgPRO.BlogApplication.mapper.CategoryMapper;
import com.orgPRO.BlogApplication.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryServiceImpl categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        System.out.println("i am from getCategories");
        List<CategoryDto> categoryDtos = categoryService.listCategories().stream().map(categoryMapper::toDto).toList();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        Category entity = categoryMapper.toEntity(createCategoryRequest);
        Category category = categoryService.createCategory(entity);
        CategoryDto dto = categoryMapper.toDto(category);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
