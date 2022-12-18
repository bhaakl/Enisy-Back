package kg.bhaakl.tssra.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class ParagraphDTO {
    @Column(name = "p")
    private String p;
}
