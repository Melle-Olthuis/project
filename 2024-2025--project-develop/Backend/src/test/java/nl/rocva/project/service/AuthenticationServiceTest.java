package nl.rocva.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import nl.rocva.project.model.User;
import nl.rocva.project.repository.AuthenticationRepository;

@SpringBootTest
public class AuthenticationServiceTest {

    @Test
    public void testPasswordEncryption() {
        AuthenticationService authenticationService = new AuthenticationService();
        String plainPassword = "securePassword123";
        String hashedPassword = authenticationService.hashPassword(plainPassword);

        // Assert that the hashed password is not null or empty
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());

        // Assert that the hashed password is different from the plain password
        assertNotEquals(plainPassword, hashedPassword);

        // Assert that the hashed password matches the plain password when verified
        assertTrue(authenticationService.verifyPassword(plainPassword, hashedPassword));
    }

    @Test
    public void testLogin() {
        String username = "testUser";
        String password = "securePassword123";

        // Mock the repository to simulate user registration
        AuthenticationRepository mockRepository = mock(AuthenticationRepository.class);
        AuthenticationService authenticationService = new AuthenticationService(mockRepository);

        when(mockRepository.existsByUsername(username)).thenReturn(false);
        when(mockRepository.findByUsername(username))
                .thenReturn(new User(username, authenticationService.hashPassword(password)));

        // Register the user
        authenticationService.register(username, password);

        // Attempt to login with correct credentials
        User loggedInUser = authenticationService.login(username, password);
        assertNotNull(loggedInUser);
        assertEquals(username, loggedInUser.getUsername());

        // Attempt to login with incorrect password
        try {
            authenticationService.login(username, "wrongPassword");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid password", e.getMessage());
        }

        // Attempt to login with non-existent username
        try {
            authenticationService.login("nonExistentUser", password);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid username", e.getMessage());
        }
    }
}
