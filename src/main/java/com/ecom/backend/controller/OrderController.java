package com.ecom.backend.controller;

import com.ecom.backend.model.Order;
import com.ecom.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @DeleteMapping("/{orderId}")
    public Map<String, Object> deleteOrder(@PathVariable String orderId) {
        try {
            orderRepository.deleteByOrderId(orderId);
            return Map.of("success", true, "message", "Order deleted: " + orderId);
        } catch (Exception e) {
            // Also try by primary key if orderId is numeric
            try {
                Long id = Long.parseLong(orderId);
                orderRepository.deleteById(id);
                return Map.of("success", true, "message", "Order deleted by id: " + id);
            } catch (Exception ignored) {
                return Map.of("success", false, "error", e.getMessage());
            }
        }
    }

    @PostMapping("/delete/{orderId}")
    public Map<String, Object> deleteOrderPost(@PathVariable String orderId) {
        return deleteOrder(orderId);
    }

    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable String orderId, @RequestBody Order updated) {
        return orderRepository.findByOrderId(orderId).map(existing -> {
            if (updated.getCustomer() != null) existing.setCustomer(updated.getCustomer());
            if (updated.getItems() != null) existing.setItems(updated.getItems());
            if (updated.getTotal() != null) existing.setTotal(updated.getTotal());
            if (updated.getShipping() != null) existing.setShipping(updated.getShipping());
            existing.setPaid(updated.isPaid());
            return orderRepository.save(existing);
        }).orElseGet(() -> orderRepository.save(updated));
    }

    @PutMapping("/{orderId}/status")
    public Map<String, Object> toggleOrderStatus(@PathVariable String orderId, @RequestBody Map<String, Object> body) {
        return orderRepository.findByOrderId(orderId).map(existing -> {
            if (body.containsKey("isPaid")) {
                existing.setPaid(Boolean.TRUE.equals(body.get("isPaid")));
            }
            orderRepository.save(existing);
            return Map.<String, Object>of("success", true, "isPaid", existing.isPaid(), "orderId", orderId);
        }).orElse(Map.<String, Object>of("success", false, "error", "Order not found: " + orderId));
    }
}

