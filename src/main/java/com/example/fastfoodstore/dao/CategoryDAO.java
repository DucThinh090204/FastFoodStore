package com.example.fastfoodstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Long> {
    
}