package kg.bhaakl.tssra.services;

import kg.bhaakl.tssra.models.Role;
import kg.bhaakl.tssra.models.Roles;
import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(Collections.singleton(new Roles(Role.USER))));
        peopleRepository.save(user);
    }
}
