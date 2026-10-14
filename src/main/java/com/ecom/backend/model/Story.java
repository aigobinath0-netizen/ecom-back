package com.ecom.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String img;

    private String time;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "story_images", joinColumns = @JoinColumn(name = "story_id"))
    @Column(name = "image_url", columnDefinition = "TEXT")
    private List<String> images = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    // Lenient setter: if Jackson or client sends a String like "story-123", cleanly extract number or null
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

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}
