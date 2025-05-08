package nl.rocva.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import nl.rocva.project.model.User;
import nl.rocva.project.repository.AuthenticationRepository;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public AuthenticationService() {
        // Default constructor for Spring to use
    }

    public AuthenticationService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    public User register(String username, String password) {
        if (authenticationRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = hashPassword(password);
        User newUser = new User(username, hashedPassword);

        return authenticationRepository.save(newUser);
    }

    public User login(String username, String password) {
        User user = authenticationRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("Invalid username");
        }

        if (!verifyPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;
    }

}
