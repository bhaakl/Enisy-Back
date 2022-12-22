package kg.bhaakl.tssra.services;

import kg.bhaakl.tssra.models.Role;
import kg.bhaakl.tssra.models.Roles;
import kg.bhaakl.tssra.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        final Roles roles1 = new Roles(Role.USER);
        roles1.setUser(user);
        final Roles roles2 = new Roles(Role.ADMIN);
        roles2.setUser(user);
        final Roles roles3 = new Roles(Role.GUEST);
        roles3.setUser(user);
        roleService.save(roles1);
        roleService.save(roles2);
        roleService.save(roles3);
    }
}
