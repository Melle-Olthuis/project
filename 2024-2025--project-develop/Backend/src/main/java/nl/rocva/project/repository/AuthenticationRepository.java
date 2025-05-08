package nl.rocva.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.rocva.project.model.User;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
