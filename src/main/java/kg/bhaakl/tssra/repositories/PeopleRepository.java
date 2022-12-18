package kg.bhaakl.tssra.repositories;

import kg.bhaakl.tssra.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String s);
}
