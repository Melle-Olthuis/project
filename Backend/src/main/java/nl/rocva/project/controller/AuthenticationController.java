package nl.rocva.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nl.rocva.project.dto.ErrorResponse;
import nl.rocva.project.dto.AuthRequest;
import nl.rocva.project.dto.AuthResponse;
import nl.rocva.project.service.AuthenticationService;
import nl.rocva.project.model.User;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest request) {
        try {
            User registeredUser = authenticationService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(registeredUser.getId(), registeredUser.getUsername(), "User registered successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Registration failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest request) {
        try {
            User authenticatedUser = authenticationService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new AuthResponse(authenticatedUser.getId(), authenticatedUser.getUsername(), "Login successful"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Login failed: " + e.getMessage()));
        }
    }
}
