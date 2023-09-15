package kg.bhaakl.enisy.controllers;

import kg.bhaakl.enisy.dto.TopicDTO;
import kg.bhaakl.enisy.dto.TopicsResponse;
import kg.bhaakl.enisy.models.Topic;
import kg.bhaakl.enisy.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topics")
public class TopicController {
    private final TopicService topicService;
    private final ModelMapper modelMapper;

    @Autowired
    public TopicController(TopicService topicService, ModelMapper modelMapper) {
        this.topicService = topicService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public TopicDTO one(@PathVariable Integer id) {
        Topic topic = topicService.getTopicByRefId(id);
        return convertToTopicDTO(topic);
    }

    @PostMapping("add")
    public ResponseEntity<Topic> create(@RequestBody TopicDTO topicDTO) {
        topicService.addTopic(convertToTopic(topicDTO));
        return ResponseEntity.ok(convertToTopic(topicDTO));
    }

    @GetMapping
    public TopicsResponse all() {
        return new TopicsResponse(topicService.findAll().stream().map(this::convertToTopicDTO)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> update(@PathVariable int id, @RequestBody TopicDTO topicDTO) {
        try {
            topicService.update(id, convertToTopic(topicDTO));
        } catch (Exception ignored) {
            return ResponseEntity.ok(Map.of("res", false));
        }
        return ResponseEntity.ok(Map.of("res", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, Object>> delete(@PathVariable int id) {
        try {
            topicService.deleteTopicById(id);
        } catch (Exception ignored) {
            return ResponseEntity.ok(Map.of("res", false));
        }
        return ResponseEntity.ok(Map.of("res", true));
    }

    private Topic convertToTopic(TopicDTO topicDTO) {
        return modelMapper.map(topicDTO, Topic.class);
    }

    private TopicDTO convertToTopicDTO(Topic topic) {
        return modelMapper.map(topic, TopicDTO.class);
    }
}
