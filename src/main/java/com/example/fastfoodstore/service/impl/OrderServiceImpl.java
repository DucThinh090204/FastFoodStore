package com.example.fastfoodstore.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastfoodstore.dao.MenuItemDAO;
import com.example.fastfoodstore.dao.OrderDAO;
import com.example.fastfoodstore.dao.UserDAO;
import com.example.fastfoodstore.dto.OrderDTO;
import com.example.fastfoodstore.dto.OrderItemDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.MenuItem;
import com.example.fastfoodstore.model.Order;
import com.example.fastfoodstore.model.OrderItem;
import com.example.fastfoodstore.model.User;
import com.example.fastfoodstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return convertToDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        if (orderDTO.getUserId() == null || orderDTO.getStatus() == null || orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("User ID, status, and at least one order item are required");
        }

        Order order = new Order();
        User user = userDAO.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + orderDTO.getUserId()));
        order.setUser(user);
        order.setStatus(orderDTO.getStatus());

        List<OrderItem> orderItems = IntStream.range(0, orderDTO.getOrderItems().size())
                .mapToObj(index -> {
                    OrderItemDTO itemDTO = orderDTO.getOrderItems().get(index);
                    MenuItem menuItem = menuItemDAO.findById(itemDTO.getMenuItemId())
                            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + itemDTO.getMenuItemId()));
                    OrderItem item = new OrderItem();
                    item.setId(index + 1);
                    item.setOrder(order);
                    item.setMenuItem(menuItem);
                    item.setQuantity(itemDTO.getQuantity());
                    double totalPrice = menuItem.getPrice() * itemDTO.getQuantity();
                    item.setTotalPrice(totalPrice);
                    return item;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());

        Order savedOrder = orderDAO.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (orderDTO.getUserId() != null) {
            User user = userDAO.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + orderDTO.getUserId()));
            order.setUser(user);
        }
        if (orderDTO.getTotalAmount() != null && orderDTO.getTotalAmount() > 0) {
            order.setTotalAmount(orderDTO.getTotalAmount());
        }
        if (orderDTO.getStatus() != null) {
            order.setStatus(orderDTO.getStatus());
        }
        if (orderDTO.getOrderItems() != null) {
            // Xóa các OrderItem cũ và thêm mới vào danh sách hiện tại
            order.getOrderItems().clear();
            IntStream.range(0, orderDTO.getOrderItems().size())
                    .forEach(index -> {
                        OrderItemDTO itemDTO = orderDTO.getOrderItems().get(index);
                        MenuItem menuItem = menuItemDAO.findById(itemDTO.getMenuItemId())
                                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found with id: " + itemDTO.getMenuItemId()));
                        OrderItem item = new OrderItem();
                        item.setId(index + 1); // Gán id mới
                        item.setOrder(order);
                        item.setMenuItem(menuItem);
                        item.setQuantity(itemDTO.getQuantity());
                        double totalPrice = menuItem.getPrice() * itemDTO.getQuantity();
                        item.setTotalPrice(totalPrice);
                        order.getOrderItems().add(item); // Thêm vào danh sách hiện tại
                    });
            // Cập nhật totalAmount
            order.setTotalAmount(order.getOrderItems().stream().mapToDouble(OrderItem::getTotalPrice).sum());
        }

        Order updatedOrder = orderDAO.save(order);
        return convertToDTO(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderDAO.delete(order);
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderItems().stream()
                        .map(this::convertOrderItemToDTO)
                        .collect(Collectors.toList())
        );
    }

    private OrderItemDTO convertOrderItemToDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getId(),
                item.getOrder().getId(),
                item.getMenuItem().getId(),
                item.getQuantity(),
                item.getTotalPrice()
        );
    }
}