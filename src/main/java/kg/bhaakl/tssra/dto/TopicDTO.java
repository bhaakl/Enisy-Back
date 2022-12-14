package kg.bhaakl.tssra.dto;

import jakarta.persistence.*;
import kg.bhaakl.tssra.models.Paragraph;

import java.util.Date;
import java.util.List;

public class TopicDTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "picture")
    private String pic;

    private List<ParagraphDTO> paragraphs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<ParagraphDTO> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<ParagraphDTO> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
