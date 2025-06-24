package com.ecommerce.aish.store.controller;


import com.ecommerce.aish.store.model.Order;
import com.ecommerce.aish.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private String getCurrentUserEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(Authentication authentication, @RequestBody Map<String, String> payload) {
        String shippingAddress = payload.get("shippingAddress");
        Order order = orderService.createOrder(getCurrentUserEmail(authentication), shippingAddress);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderHistory(Authentication authentication) {
        List<Order> orders = orderService.getOrderHistoryForUser(getCurrentUserEmail(authentication));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        Order.OrderStatus status = Order.OrderStatus.valueOf(payload.get("status"));
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }
}