package kg.bhaakl.tssra.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    @NotEmpty(message = "Username should no be empty!")
    @NotNull
    private String username;

    @NotNull
    private String password;
}
