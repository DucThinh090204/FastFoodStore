package com.example.fastfoodstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.Order;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {
    // Tìm đơn hàng theo user
    List<Order> findByUserId(Long userId);
    
    // Tìm đơn hàng theo trạng thái
    List<Order> findByStatus(String status);
}
