package kg.bhaakl.enisy.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Topic")
public class Topic extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "picture")
    private String pic;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paragraph> paragraphs = new ArrayList<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    public void addParagraph(Paragraph paragraph) {
        paragraphs.add(paragraph);
        paragraph.setTopic(this);
    }

    public void removeParagraph(Paragraph paragraph) {
        paragraphs.remove(paragraph);
        paragraph.setTopic(null);
    }

    public void addImages(Image image) {
        images.add(image);
        image.setTopic(this);
    }

    public void removeImages(Image image) {
        images.remove(image);
        image.setTopic(null);
    }
}
