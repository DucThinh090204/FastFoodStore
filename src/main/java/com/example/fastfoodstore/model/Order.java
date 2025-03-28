package com.example.fastfoodstore.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Customer id is required")
    @JoinColumn(name = "user_id")
    private User user;

    @Min(value = 0, message = "Total amount must be greater than or equal to 0")
    @Column(name = "total_amount", nullable = false)
    private double totalAmount; // Giữ kiểu double

    @NotBlank(message = "Status is required")
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty(message = "Order must contain at least one OrderItem")
    @JsonManagedReference
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Long id, List<OrderItem> orderItems, String status, double totalAmount, User user) {
        this.id = id;
        this.orderItems = orderItems;
        this.status = status;
        this.totalAmount = totalAmount;
        this.user = user;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double calculateTotalAmount() {
        return orderItems.stream()
                .mapToDouble(item -> item.getTotalPrice() * item.getQuantity())
                .sum();
    }
}
