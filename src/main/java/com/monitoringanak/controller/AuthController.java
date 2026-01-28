package com.monitoringanak.controller;

import com.monitoringanak.model.User;
import com.monitoringanak.dto.*;
import com.monitoringanak.service.UserService;
import com.monitoringanak.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Login endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.login(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Verify password
            if (!userService.verifyPassword(request.getPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.builder()
                                .success(false)
                                .message("Password incorrect")
                                .code(401)
                                .build());
            }

            // Generate JWT token
            String token = jwtTokenProvider.generateToken(user.getIdUser(), user.getUsername(),
                    user.getRole().getNamaRole());

            LoginResponse response = LoginResponse.builder()
                    .idUser(user.getIdUser())
                    .username(user.getUsername())
                    .nama(user.getNama())
                    .role(user.getRole().getNamaRole())
                    .token(token)
                    .message("Login successful")
                    .build();

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Login successful")
                            .data(response)
                            .code(200)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }

    /**
     * Register endpoint (admin only)
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(
                    request.getUsername(),
                    request.getPassword(),
                    request.getNama(),
                    request.getEmail(),
                    request.getIdRole());

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("User registered successfully")
                            .data(user)
                            .code(200)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .code(400)
                            .build());
        }
    }
}
