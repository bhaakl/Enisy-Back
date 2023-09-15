package kg.bhaakl.enisy.repositories;

import kg.bhaakl.enisy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findFirstByUsername(String s);
}
