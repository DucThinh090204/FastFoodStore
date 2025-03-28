package com.example.fastfoodstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastfoodstore.dao.DeliveryDAO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.Delivery;
import com.example.fastfoodstore.service.DeliveryService;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryDAO deliveryDAO;

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryDAO.findAll();
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveryDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
    }

    @Override
    public Delivery getDeliveryByOrder(Long orderId) {
        Delivery delivery = deliveryDAO.findByOrderId(orderId);
        if (delivery == null) {
            throw new ResourceNotFoundException("Delivery not found for order id: " + orderId);
        }
        return delivery;
    }

    @Override
    public Delivery createDelivery(Delivery delivery) {
        return deliveryDAO.save(delivery);
    }

    @Override
    public Delivery updateDeliveryStatus(Long id, String status) {
        Delivery existingDelivery = getDeliveryById(id);
        existingDelivery.setDeliveryStatus(status);
        return deliveryDAO.save(existingDelivery);
    }
}