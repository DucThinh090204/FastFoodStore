package com.example.fastfoodstore.dto;

public class OrderItemDTO {
    private int id; // Không bắt buộc khi tạo mới
    private Long orderId; // Không cần khi tạo mới
    private Long menuItemId; // Bắt buộc
    private int quantity; // Bắt buộc
    private Double totalPrice; // Không bắt buộc, sẽ được tính tự động

    // Constructors
    public OrderItemDTO() {}

    public OrderItemDTO(int id, Long orderId, Long menuItemId, int quantity, Double totalPrice) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // Getters, setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Long menuItemId) { this.menuItemId = menuItemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
}