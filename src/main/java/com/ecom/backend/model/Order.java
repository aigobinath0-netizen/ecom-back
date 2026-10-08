package com.ecom.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer_orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private String date;

    @Column(columnDefinition = "TEXT")
    private String customer; 

    @Column(columnDefinition = "TEXT")
    private String items; 

    private String total;

    private String shipping;

    private boolean isPaid;
}
