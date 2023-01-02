package kg.bhaakl.enisy.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
