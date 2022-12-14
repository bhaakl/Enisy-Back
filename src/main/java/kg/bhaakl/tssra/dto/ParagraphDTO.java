package kg.bhaakl.tssra.dto;

import jakarta.persistence.Column;
import kg.bhaakl.tssra.models.Topic;

public class ParagraphDTO {
    @Column(name = "p")
    private String p;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
