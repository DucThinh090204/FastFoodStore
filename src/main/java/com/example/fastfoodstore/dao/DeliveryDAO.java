package com.example.fastfoodstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.Delivery;

@Repository
public interface DeliveryDAO extends JpaRepository<Delivery, Long> {
    // Tìm delivery theo order
    Delivery findByOrderId(Long orderId);
}