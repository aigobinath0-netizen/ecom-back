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
    private String section; // "phone_cover", "temper_glass", "laptop_skin"
    @com.fasterxml.jackson.annotation.JsonProperty("hasTwoPhotos")
    @Column(name = "has_two_photos")
    private Boolean hasTwoPhotos = false;

    public Boolean getHasTwoPhotos() {
        return hasTwoPhotos != null ? hasTwoPhotos : false;
    }

    public void setHasTwoPhotos(Boolean hasTwoPhotos) {
        this.hasTwoPhotos = hasTwoPhotos != null ? hasTwoPhotos : false;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("hasGoldPlate")
    @Column(name = "has_gold_plate")
    private Boolean hasGoldPlate = false;

    public Boolean getHasGoldPlate() {
        return hasGoldPlate != null ? hasGoldPlate : false;
    }

    public void setHasGoldPlate(Boolean hasGoldPlate) {
        this.hasGoldPlate = hasGoldPlate != null ? hasGoldPlate : false;
    }
}