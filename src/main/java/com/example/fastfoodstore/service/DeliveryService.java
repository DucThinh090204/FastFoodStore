package com.example.fastfoodstore.service;

import java.util.List;

import com.example.fastfoodstore.model.Delivery;

public interface DeliveryService {
    List<Delivery> getAllDeliveries();
    Delivery getDeliveryById(Long id);
    Delivery getDeliveryByOrder(Long orderId);
    Delivery createDelivery(Delivery delivery);
    Delivery updateDeliveryStatus(Long id, String status);
}