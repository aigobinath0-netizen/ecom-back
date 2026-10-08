package com.ecom.backend.controller;

import com.ecom.backend.model.Review;
import com.ecom.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review updated) {
        return reviewRepository.findById(id).map(review -> {
            if (updated.getAuthor() != null) review.setAuthor(updated.getAuthor());
            if (updated.getTitle() != null) review.setTitle(updated.getTitle());
            if (updated.getText() != null) review.setText(updated.getText());
            review.setRating(updated.getRating());
            if (updated.getProduct() != null) review.setProduct(updated.getProduct());
            review.setVerified(updated.isVerified());
            if (updated.getImg() != null) review.setImg(updated.getImg());
            return reviewRepository.save(review);
        }).orElseGet(() -> {
            updated.setId(id);
            return reviewRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }

    @PostMapping("/delete/{id}")
    public void deleteReviewPost(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }
}
