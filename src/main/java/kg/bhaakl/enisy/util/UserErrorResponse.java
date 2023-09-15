package kg.bhaakl.enisy.util;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {
    private Boolean res;
    private List<String> errors = new ArrayList<>();
    private long timestamp;

    public UserErrorResponse(Boolean res, String error, long timestamp) {
        this.res = res;
        this.errors.add(error);
        this.timestamp = timestamp;
    }
}
