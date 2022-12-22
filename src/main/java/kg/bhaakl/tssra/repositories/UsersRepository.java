package kg.bhaakl.tssra.repositories;

import kg.bhaakl.tssra.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String s);
}
