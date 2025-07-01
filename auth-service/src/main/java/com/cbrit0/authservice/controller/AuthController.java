package com.cbrit0.authservice.controller;

import com.cbrit0.authservice.dto.LoginRequestDTO;
import com.cbrit0.authservice.dto.LoginResponseDTO;
import com.cbrit0.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Generate a JWT token for user authentication")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if (tokenOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
        } else {
            String token = tokenOptional.get();
            return ResponseEntity.ok(new LoginResponseDTO(token)); // Return the JWT token
        }
    }

    @Operation(summary = "Token validation endpoint")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
        }

        return authService.validateToken(authHeader.substring(7))
                ? ResponseEntity.ok().build() // Token is valid
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Unauthorized
    }
}
