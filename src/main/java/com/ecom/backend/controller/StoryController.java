package com.ecom.backend.controller;

import com.ecom.backend.model.Story;
import com.ecom.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/stories", produces = "application/json")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class StoryController {
    
    @Autowired
    private StoryRepository storyRepository;

    @GetMapping
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Story createStory(@RequestBody Story story) {
        story.setId(null);
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

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Story updateStory(@PathVariable String id, @RequestBody Story updated) {
        Long numericId = parseLongId(id);
        if (numericId == null) {
            updated.setId(null);
            return storyRepository.save(updated);
        }
        return storyRepository.findById(numericId).map(story -> {
            if (updated.getName() != null) story.setName(updated.getName());
            if (updated.getImg() != null) story.setImg(updated.getImg());
            if (updated.getTime() != null) story.setTime(updated.getTime());
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
            storyRepository.findById(numericId).ifPresent(storyRepository::delete);
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
