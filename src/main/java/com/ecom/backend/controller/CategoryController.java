package com.ecom.backend.controller;

import com.ecom.backend.model.Category;
import com.ecom.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    private String inferSection(Category c) {
        if (c.getSection() != null && !c.getSection().trim().isEmpty()) {
            return c.getSection();
        }
        String t = (c.getTitle() != null ? c.getTitle() : "").toLowerCase();
        String s = (c.getSlug() != null ? c.getSlug() : "").toLowerCase();
        if (t.contains("temper") || t.contains("lens") || t.contains("screen") || t.contains("toughened") || s.contains("temper") || s.contains("glass-shield")) {
            return "temper_glass";
        }
        if (t.contains("skin") || t.contains("wrap") || t.contains("carbon") || s.contains("skin") || s.contains("wrap")) {
            return "laptop_skin";
        }
        return "phone_cover";
    }

    @GetMapping
    public List<Category> getAllCategories(@RequestParam(required = false) String section) {
        List<Category> all = categoryRepository.findAll();
        for (Category c : all) {
            if (c.getSection() == null || c.getSection().trim().isEmpty()) {
                c.setSection(inferSection(c));
            }
        }
        if (section != null && !section.trim().isEmpty() && !"all".equalsIgnoreCase(section)) {
            return all.stream()
                .filter(c -> section.trim().equalsIgnoreCase(c.getSection()))
                .toList();
        }
        return all;
    }

    @PostMapping
    public Category createOrUpdateCategory(@RequestBody Category category) {
        if (category.getSection() == null || category.getSection().trim().isEmpty()) {
            category.setSection(inferSection(category));
        }
        if (category.getId() == null || category.getId().isEmpty()) {
            category.setId("cat-" + System.currentTimeMillis());
            return categoryRepository.save(category);
        }
        return categoryRepository.findById(category.getId()).map(existing -> {
            if (category.getTitle() != null) existing.setTitle(category.getTitle());
            if (category.getSlug() != null) existing.setSlug(category.getSlug());
            if (category.getImage() != null) existing.setImage(category.getImage());
            if (category.getBannerImage() != null) existing.setBannerImage(category.getBannerImage());
            if (category.getBannerType() != null) existing.setBannerType(category.getBannerType());
            if (category.getBannerHeadline() != null) existing.setBannerHeadline(category.getBannerHeadline());
            if (category.getStartingPrice() != null) existing.setStartingPrice(category.getStartingPrice());
            if (category.getSection() != null) existing.setSection(category.getSection());
            return categoryRepository.save(existing);
        }).orElseGet(() -> categoryRepository.save(category));
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable String id, @RequestBody Category updated) {
        if (updated.getSection() == null || updated.getSection().trim().isEmpty()) {
            updated.setSection(inferSection(updated));
        }
        return categoryRepository.findById(id).map(existing -> {
            if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
            if (updated.getSlug() != null) existing.setSlug(updated.getSlug());
            if (updated.getImage() != null) existing.setImage(updated.getImage());
            if (updated.getBannerImage() != null) existing.setBannerImage(updated.getBannerImage());
            if (updated.getBannerType() != null) existing.setBannerType(updated.getBannerType());
            if (updated.getBannerHeadline() != null) existing.setBannerHeadline(updated.getBannerHeadline());
            if (updated.getStartingPrice() != null) existing.setStartingPrice(updated.getStartingPrice());
            if (updated.getSection() != null) existing.setSection(updated.getSection());
            return categoryRepository.save(existing);
        }).orElseGet(() -> {
            updated.setId(id);
            return categoryRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        categoryRepository.deleteById(id);
    }

    @PostMapping("/delete/{id}")
    public void deleteCategoryPost(@PathVariable String id) {
        categoryRepository.deleteById(id);
    }
}