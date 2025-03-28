package com.example.fastfoodstore.service.impl;

import com.example.fastfoodstore.dao.OrderDAO;
import com.example.fastfoodstore.dao.OrderItemDAO;
import com.example.fastfoodstore.dao.MenuItemDAO;
import com.example.fastfoodstore.dto.OrderItemDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.OrderItem;
import com.example.fastfoodstore.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDAO orderItemDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + id));
        return convertToDTO(orderItem);
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        mapDTOToEntity(orderItemDTO, orderItem);
        OrderItem savedOrderItem = orderItemDAO.save(orderItem);
        return convertToDTO(savedOrderItem);
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + id));
        if (orderItemDTO.getOrderId() != null) {
            orderItem.setOrder(orderDAO.findById(orderItemDTO.getOrderId()).orElse(null));
        }
        if (orderItemDTO.getMenuItemId() != null) {
            orderItem.setMenuItem(menuItemDAO.findById(orderItemDTO.getMenuItemId()).orElse(null));
        }
        if (orderItemDTO.getQuantity() > 0) {
            orderItem.setQuantity(orderItemDTO.getQuantity());
        }
        if (orderItemDTO.getTotalPrice() > 0) {
            orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
        }
        OrderItem updatedOrderItem = orderItemDAO.save(orderItem);
        return convertToDTO(updatedOrderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + id));
        orderItemDAO.delete(orderItem);
    }

    private OrderItemDTO convertToDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getMenuItem().getId(),
                orderItem.getQuantity(),
                orderItem.getTotalPrice()
        );
    }

    private void mapDTOToEntity(OrderItemDTO orderItemDTO, OrderItem orderItem) {
        orderItem.setOrder(orderDAO.findById(orderItemDTO.getOrderId()).orElse(null));
        orderItem.setMenuItem(menuItemDAO.findById(orderItemDTO.getMenuItemId()).orElse(null));
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
    }
}