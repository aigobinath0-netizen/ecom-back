package com.ecom.backend.controller;

import com.ecom.backend.model.Review;
import com.ecom.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reviews", produces = "application/json")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Review createReview(@RequestBody Review review) {
        review.setId(null);
        return reviewRepository.save(review);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Review updateReview(@PathVariable String id, @RequestBody Review updated) {
        Long numericId = parseLongId(id);
        if (numericId == null) {
            updated.setId(null);
            return reviewRepository.save(updated);
        }
        return reviewRepository.findById(numericId).map(review -> {
            if (updated.getAuthor() != null)
                review.setAuthor(updated.getAuthor());
            if (updated.getTitle() != null)
                review.setTitle(updated.getTitle());
            if (updated.getText() != null)
                review.setText(updated.getText());
            review.setRating(updated.getRating());
            if (updated.getProduct() != null)
                review.setProduct(updated.getProduct());
            review.setVerified(updated.isVerified());
            if (updated.getImg() != null)
                review.setImg(updated.getImg());
            return reviewRepository.save(review);
        }).orElseGet(() -> {
            updated.setId(numericId);
            return reviewRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        Long numericId = parseLongId(id);
        if (numericId != null) {
            reviewRepository.findById(numericId).ifPresent(reviewRepository::delete);
        }
    }

    @PostMapping("/delete/{id}")
    public void deleteReviewPost(@PathVariable String id) {
        deleteReview(id);
    }

    private Long parseLongId(String id) {
        if (id == null)
            return null;
        try {
            return Long.parseLong(id.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
