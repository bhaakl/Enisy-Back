package kg.bhaakl.enisy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Имя пользователья не может быть пустым!")
    @NotNull(message = "Это поле не может быть Nullable")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Пароль не может быть пустым!")
    @NotNull(message = "Это поле не может быть Nullable")
    @Column(name = "password")
    private String password;

}
