package com.ecom.backend.controller;

import com.ecom.backend.model.Category;
import com.ecom.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createOrUpdateCategory(@RequestBody Category category) {
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
            return categoryRepository.save(existing);
        }).orElseGet(() -> categoryRepository.save(category));
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable String id, @RequestBody Category updated) {
        return categoryRepository.findById(id).map(existing -> {
            if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
            if (updated.getSlug() != null) existing.setSlug(updated.getSlug());
            if (updated.getImage() != null) existing.setImage(updated.getImage());
            if (updated.getBannerImage() != null) existing.setBannerImage(updated.getBannerImage());
            if (updated.getBannerType() != null) existing.setBannerType(updated.getBannerType());
            if (updated.getBannerHeadline() != null) existing.setBannerHeadline(updated.getBannerHeadline());
            if (updated.getStartingPrice() != null) existing.setStartingPrice(updated.getStartingPrice());
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