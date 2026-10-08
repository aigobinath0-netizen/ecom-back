package com.ecom.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private int rating;
    private String title;
    private String text;
    private String product;
    private boolean verified;
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String img;
    private LocalDateTime createdAt = LocalDateTime.now();
}
