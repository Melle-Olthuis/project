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

    public User registerUser(String username, String email, String plainPassword) {
        if (authenticationRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (authenticationRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        String hashedPassword = hashPassword(plainPassword);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("USER");
        return authenticationRepository.save(user);
    }

    public User login(String username, String password) {
        User user = authenticationRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));

        if (!verifyPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;
    }

}
