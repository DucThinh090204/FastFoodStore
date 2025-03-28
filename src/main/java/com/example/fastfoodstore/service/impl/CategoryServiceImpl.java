package com.example.fastfoodstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fastfoodstore.dao.CategoryDAO;
import com.example.fastfoodstore.dto.CategoryDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.Category;
import com.example.fastfoodstore.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return convertToDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        Category savedCategory = categoryDAO.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        Category updatedCategory = categoryDAO.save(category);
        return convertToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryDAO.delete(category);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}