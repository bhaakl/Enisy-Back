package kg.bhaakl.tssra.services;

import kg.bhaakl.tssra.models.User;
import kg.bhaakl.tssra.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Optional<User> getUserByLogin(String l) {
        return usersRepository.findFirstByUsername(l);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    public User getRefById(int id) throws EntityNotFoundException{
        return usersRepository.getReferenceById(id);
    }

}
