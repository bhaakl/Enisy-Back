package kg.bhaakl.enisy.services;

import kg.bhaakl.enisy.models.Role;
import kg.bhaakl.enisy.models.Roles;
import kg.bhaakl.enisy.models.User;
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
        roleService.save(roles1);
    }
}
