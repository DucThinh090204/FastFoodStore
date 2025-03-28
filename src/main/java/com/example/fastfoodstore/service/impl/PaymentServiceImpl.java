package com.example.fastfoodstore.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.fastfoodstore.dao.OrderDAO;
import com.example.fastfoodstore.dao.PaymentDAO;
import com.example.fastfoodstore.dto.PaymentDTO;
import com.example.fastfoodstore.exception.ResourceNotFoundException;
import com.example.fastfoodstore.model.Order;
import com.example.fastfoodstore.model.Payment;
import com.example.fastfoodstore.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return convertToDTO(payment);
    }

    @Override
    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        if (paymentDTO.getOrderId() == null || paymentDTO.getPaymentMethod() == null || paymentDTO.getPaymentStatus() == null) {
            throw new IllegalArgumentException("Order ID, payment method, and payment status are required");
        }

        Payment payment = new Payment();
        Order order = orderDAO.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + paymentDTO.getOrderId()));
        payment.setOrder(order);
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus(paymentDTO.getPaymentStatus());
        payment.setPaymentDate(paymentDTO.getPaymentDate() != null ? paymentDTO.getPaymentDate() : LocalDateTime.now());

        Payment savedPayment = paymentDAO.save(payment);
        return convertToDTO(savedPayment);
    }

    @Override
    @Transactional
    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = paymentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        if (paymentDTO.getOrderId() != null) {
            Order order = orderDAO.findById(paymentDTO.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + paymentDTO.getOrderId()));
            payment.setOrder(order);
        }
        if (paymentDTO.getPaymentMethod() != null) {
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        }
        if (paymentDTO.getPaymentStatus() != null) {
            // Cập nhật paymentStatus và paymentDate cùng lúc
            payment.setPaymentStatus(paymentDTO.getPaymentStatus());
            payment.setPaymentDate(LocalDateTime.now()); // Tự động cập nhật thời gian hiện tại
        }
        // Nếu client gửi paymentDate, ưu tiên giá trị đó (tùy chọn, có thể bỏ nếu luôn muốn dùng thời gian hiện tại)
        if (paymentDTO.getPaymentDate() != null) {
            payment.setPaymentDate(paymentDTO.getPaymentDate());
        }

        Payment updatedPayment = paymentDAO.save(payment);
        return convertToDTO(updatedPayment);
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        Payment payment = paymentDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        paymentDAO.delete(payment);
    }

    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getPaymentDate()
        );
    }
}