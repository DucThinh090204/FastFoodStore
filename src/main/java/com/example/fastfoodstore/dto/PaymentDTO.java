package com.example.fastfoodstore.dto;

import java.time.LocalDateTime;

public class PaymentDTO {
    private Long id;
    private Long orderId; // Chỉ lưu id của Order
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime paymentDate;

    // Constructors
    public PaymentDTO() {}

    public PaymentDTO(Long id, Long orderId, String paymentMethod, String paymentStatus, LocalDateTime paymentDate) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // Getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}