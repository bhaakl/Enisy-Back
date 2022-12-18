package kg.bhaakl.tssra.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@NoArgsConstructor
@Getter
@Entity
@Table(name = "paragraphs")
public class Paragraph {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "p")
    private String p;

    @Column(name = "topic_id")
    private Integer topicId;
}
