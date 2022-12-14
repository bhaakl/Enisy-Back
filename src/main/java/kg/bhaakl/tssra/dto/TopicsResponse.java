package kg.bhaakl.tssra.dto;

import java.util.List;

public class TopicsResponse {
    private List<TopicDTO> topics;

    public TopicsResponse(List<TopicDTO> topics) {
        this.topics = topics;
    }

    public List<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDTO> topics) {
        this.topics = topics;
    }
}
