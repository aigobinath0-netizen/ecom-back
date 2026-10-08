package com.ecom.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    private String id;
    private String categoryId;
    private String title;
    private Double price;
    private Double oldPrice;
    private String off;
    private String badge;

    @Column(columnDefinition = "TEXT")
    private String image;
}