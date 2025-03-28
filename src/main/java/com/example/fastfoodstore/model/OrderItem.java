package com.example.fastfoodstore.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_item")
@IdClass(OrderItem.OrderItemId.class)
public class OrderItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull(message = "Menu item is required")
    @ManyToOne
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Min(value = 0, message = "Unit price must be greater than or equal to 0")
    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    public OrderItem(int id, Order order, MenuItem menuItem, int quantity, double totalPrice) {
        this.id = id;
        this.order = order;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public OrderItem() {}

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public MenuItem getMenuItem() {
        return menuItem;
    }
    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public static class OrderItemId implements Serializable {
        private Long order; // Chỉ cần order_id
        private int id;

        public OrderItemId() {}

        public OrderItemId(Long order, int id) {
            this.order = order;
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderItemId that = (OrderItemId) o;
            return id == that.id && order.equals(that.order);
        }

        @Override
        public int hashCode() {
            return 31 * order.hashCode() + id;
        }
    }
}
