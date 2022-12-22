package kg.bhaakl.tssra.services;

import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class USERDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RoleService roleService;

    @Autowired
    public USERDetailsService(UsersRepository usersRepository, RoleService roleService) {
        this.usersRepository = usersRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findFirstByUsername(s);
        if (user.isEmpty())
            throw new UsernameNotFoundException("Unknown user '" + s + "'!");

        final String roles = roleService.getRolesOfUserById(user.get().getId())
                .stream().map(r -> r.getRole().getAuthority()).collect(Collectors.joining(","));
        final UserDetails ud = org.springframework.security.core.userdetails.User.builder()
                .username(user.get().getUsername())
                .password(user.get().getPassword())
                .roles(roles.split(","))
                .build();
        return ud;
    }
}

