package kg.bhaakl.tssra.dto;

import kg.bhaakl.tssra.models.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class RoleDTO {
    @Column(name = "u_role")
    @Enumerated(EnumType.STRING)
    private Role role;
}
