package com.ecom.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Category {
    @Id
    private String id;
    private String title;
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String bannerImage;

    private String bannerType;
    private String bannerHeadline;
    private Integer startingPrice;
}