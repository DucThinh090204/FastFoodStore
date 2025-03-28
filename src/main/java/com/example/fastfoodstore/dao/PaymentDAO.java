package com.example.fastfoodstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fastfoodstore.model.Payment;

@Repository
public interface PaymentDAO extends JpaRepository<Payment, Long> {
    // Tìm payment theo order
    Payment findByOrderId(Long orderId);
}