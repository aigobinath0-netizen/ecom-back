package com.ecom.backend.controller;

import com.ecom.backend.model.Product;
import com.ecom.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String categoryId) {
        if (categoryId != null && !categoryId.trim().isEmpty() && !"all".equalsIgnoreCase(categoryId)) {
            return productRepository.findByCategoryId(categoryId);
        }
        return productRepository.findAll();
    }

    @PostMapping
    public Product createOrUpdateProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId("prod-" + System.currentTimeMillis());
            return productRepository.save(product);
        }
        return productRepository.findById(product.getId()).map(existing -> {
            if (product.getCategoryId() != null) existing.setCategoryId(product.getCategoryId());
            if (product.getTitle() != null) existing.setTitle(product.getTitle());
            if (product.getPrice() != null) existing.setPrice(product.getPrice());
            if (product.getOldPrice() != null) existing.setOldPrice(product.getOldPrice());
            if (product.getOff() != null) existing.setOff(product.getOff());
            if (product.getBadge() != null) existing.setBadge(product.getBadge());
            if (product.getImage() != null) existing.setImage(product.getImage());
            return productRepository.save(existing);
        }).orElseGet(() -> productRepository.save(product));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updated) {
        return productRepository.findById(id).map(existing -> {
            if (updated.getCategoryId() != null) existing.setCategoryId(updated.getCategoryId());
            if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
            if (updated.getPrice() != null) existing.setPrice(updated.getPrice());
            if (updated.getOldPrice() != null) existing.setOldPrice(updated.getOldPrice());
            if (updated.getOff() != null) existing.setOff(updated.getOff());
            if (updated.getBadge() != null) existing.setBadge(updated.getBadge());
            if (updated.getImage() != null) existing.setImage(updated.getImage());
            return productRepository.save(existing);
        }).orElseGet(() -> {
            updated.setId(id);
            return productRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productRepository.deleteById(id);
    }

    @PostMapping("/delete/{id}")
    public void deleteProductPost(@PathVariable String id) {
        productRepository.deleteById(id);
    }
}