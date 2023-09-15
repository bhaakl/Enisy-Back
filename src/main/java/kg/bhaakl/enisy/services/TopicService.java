package kg.bhaakl.enisy.services;

import kg.bhaakl.enisy.models.Topic;
import kg.bhaakl.enisy.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Transactional
    public void addTopic(Topic topic) {
        topic.getParagraphs().forEach(e -> e.setTopic(topic));
        topic.setCreatedAt(new Date());
        topicRepository.save(topic);
    }

    public Topic getTopicByRefId(Integer id) throws EntityNotFoundException {
        return topicRepository.getReferenceById(id);
    }

    @Transactional
    public void deleteTopicById(Integer id) {
        topicRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Topic newTopic) throws EntityNotFoundException {
        Topic topic = getTopicByRefId(id);
        newTopic.setId(id);
        newTopic.setCreatedAt(topic.getCreatedAt());
        newTopic.setParagraphs(topic.getParagraphs());
        topicRepository.save(newTopic);
    }
}
