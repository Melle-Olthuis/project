package nl.rocva.project.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidEmail() {
        User user = new User();
        user.setUsername("jan");
        user.setEmail("jan@example.com");
        user.setPassword("welkom123");
        user.setRole("USER");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "Er mogen geen fouten zijn voor een geldig e-mailadres");
    }

    @Test
    public void testInvalidEmail() {
        User user = new User();
        user.setUsername("jan");
        user.setEmail("geen-geldig-email");
        user.setPassword("welkom123");
        user.setRole("USER");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty(), "Er moet een validatiefout zijn voor een ongeldig e-mailadres");

        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
    }
}
