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

    public void setId(Long id) {
        this.id = id;
    }

    // Lenient setter: if Jackson or client sends a String like "rev-123", cleanly extract number or null
    public void setId(Object id) {
        if (id instanceof Number) {
            this.id = ((Number) id).longValue();
        } else if (id instanceof String) {
            try {
                String clean = ((String) id).replaceAll("[^0-9]", "");
                this.id = clean.isEmpty() ? null : Long.parseLong(clean);
            } catch (Exception e) {
                this.id = null;
            }
        } else {
            this.id = null;
        }
    }
}
