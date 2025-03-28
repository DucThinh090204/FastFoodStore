package com.example.fastfoodstore.dto;

import java.util.List;

public class OrderDTO {
    private Long id; // Không cần khi tạo mới
    private Long userId; // Bắt buộc
    private Double totalAmount; // Không bắt buộc, sẽ được tính tự động
    private String status; // Bắt buộc
    private List<OrderItemDTO> orderItems; // Bắt buộc

    // Constructors
    public OrderDTO() {}

    public OrderDTO(Long id, Long userId, Double totalAmount, String status, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = orderItems;
    }

    // Getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }
}