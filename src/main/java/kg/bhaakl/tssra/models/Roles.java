package kg.bhaakl.tssra.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "u_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public Roles(Role role) {
        this.role = role;
    }

    public Roles() {
    }
}
