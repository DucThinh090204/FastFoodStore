package com.example.fastfoodstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.OrderItem;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItem, Long> {
    // Tìm order item theo order
    List<OrderItem> findByOrderId(Long orderId);
}