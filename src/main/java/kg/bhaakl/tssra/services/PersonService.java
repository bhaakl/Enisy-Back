package kg.bhaakl.tssra.services;

import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<User> getUserByLogin(String l) {
        return peopleRepository.findByUsername(l);
    }
}
