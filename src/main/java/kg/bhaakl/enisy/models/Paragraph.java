package kg.bhaakl.enisy.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@NoArgsConstructor
@Getter
@Entity
@Table(name = "paragraphs")
public class Paragraph extends BaseEntity {

    @Column(name = "p")
    private String p;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;
}
