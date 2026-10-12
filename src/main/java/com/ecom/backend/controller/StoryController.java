package com.ecom.backend.controller;

import com.ecom.backend.model.Story;
import com.ecom.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "*")
public class StoryController {
    
    @Autowired
    private StoryRepository storyRepository;

    @GetMapping
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @PostMapping
    public Story createStory(@RequestBody Story story) {
        if (story.getImages() == null) {
            story.setImages(new java.util.ArrayList<>());
        }
        if (!story.getImages().isEmpty()) {
            if (story.getImg() == null || story.getImg().isEmpty()) {
                story.setImg(story.getImages().get(0));
            }
        } else if (story.getImg() != null && !story.getImg().isEmpty()) {
            story.getImages().add(story.getImg());
        }
        return storyRepository.save(story);
    }

    @PutMapping("/{id}")
    public Story updateStory(@PathVariable String id, @RequestBody Story updated) {
        Long numericId = parseLongId(id);
        if (numericId == null) {
            return storyRepository.save(updated);
        }
        return storyRepository.findById(numericId).map(story -> {
            if (updated.getName() != null) story.setName(updated.getName());
            if (updated.getImg() != null) story.setImg(updated.getImg());
            if (updated.getImages() != null) story.setImages(updated.getImages());
            return storyRepository.save(story);
        }).orElseGet(() -> {
            updated.setId(numericId);
            return storyRepository.save(updated);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteStory(@PathVariable String id) {
        Long numericId = parseLongId(id);
        if (numericId != null) {
            storyRepository.deleteById(numericId);
        }
    }

    @PostMapping("/delete/{id}")
    public void deleteStoryPost(@PathVariable String id) {
        deleteStory(id);
    }

    private Long parseLongId(String id) {
        if (id == null) return null;
        try {
            return Long.parseLong(id.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
