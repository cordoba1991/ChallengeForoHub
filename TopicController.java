package com.example.foroHub.controller;

import com.example.foroHub.model.Topic;
import com.example.foroHub.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@Validated
public class TopicController {
    
    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@Valid @RequestBody Topic topic) {
        Topic savedTopic = topicRepository.save(topic);
        return new ResponseEntity<>(savedTopic, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));
        return ResponseEntity.ok(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @Valid @RequestBody Topic topicDetails) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topic.setTitle(topicDetails.getTitle());
        topic.setContent(topicDetails.getContent());

        Topic updatedTopic = topicRepository.save(topic);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic not found"));

        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }
}
