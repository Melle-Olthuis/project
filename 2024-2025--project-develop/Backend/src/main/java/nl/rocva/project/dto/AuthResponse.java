package nl.rocva.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long id;
    private String username;
    private String message;
    private String token; // Can be used for JWT authentication in the future
    
    public AuthResponse(Long id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }
}