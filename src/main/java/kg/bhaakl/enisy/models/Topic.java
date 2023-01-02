package kg.bhaakl.enisy.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "picture")
    private String pic;

    @OneToMany
    @JoinTable(
            name="paragraphs",
            joinColumns = @JoinColumn( name="topic_id"),
            inverseJoinColumns = @JoinColumn( name="id")
    )
    private List<Paragraph> paragraphs;
}
