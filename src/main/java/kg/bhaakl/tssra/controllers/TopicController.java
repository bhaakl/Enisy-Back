package kg.bhaakl.tssra.controllers;

import kg.bhaakl.tssra.dto.TopicDTO;
import kg.bhaakl.tssra.dto.TopicsResponse;
import kg.bhaakl.tssra.models.Topic;
import kg.bhaakl.tssra.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:8081"})
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

    @GetMapping
    public TopicsResponse all() {
        return new TopicsResponse(topicService.findAll().stream().map(this::convertToTopicDTO)
                .collect(Collectors.toList()));
    }

    private Topic convertToTopic(TopicDTO topicDTO) {
        return modelMapper.map(topicDTO, Topic.class);
    }

    private TopicDTO convertToTopicDTO(Topic topic) {
        return modelMapper.map(topic, TopicDTO.class);
    }
}
