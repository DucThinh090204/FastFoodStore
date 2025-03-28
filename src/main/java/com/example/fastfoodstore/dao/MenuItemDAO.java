package com.example.fastfoodstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.MenuItem;

@Repository
public interface MenuItemDAO extends JpaRepository<MenuItem, Long> {
    // Tìm menu item theo category
    List<MenuItem> findByCategory_Id(Long categoryId);
}
