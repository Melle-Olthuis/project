package nl.rocva.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.rocva.project.model.User;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
