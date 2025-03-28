package com.example.fastfoodstore.service;

import java.util.List;

import com.example.fastfoodstore.dto.OrderItemDTO;

public interface OrderItemService {
    List<OrderItemDTO> getAllOrderItems();
    OrderItemDTO getOrderItemById(Long id);
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);
    void deleteOrderItem(Long id);
}