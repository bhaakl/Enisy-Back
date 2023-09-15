package kg.bhaakl.enisy.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TopicDTO {

    private Integer id;

    private String title;

    private Date createdAt;

    private String pic;

    private List<ParagraphDTO> paragraphs;
}
