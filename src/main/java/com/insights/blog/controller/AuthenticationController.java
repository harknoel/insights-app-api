package com.insights.blog.controller;

import com.insights.blog.exception.UserAlreadyExistsException;
import com.insights.blog.payload.LoginRequestDTO;
import com.insights.blog.payload.AuthenticationResponseDTO;
import com.insights.blog.payload.RegisterRequestDTO;
import com.insights.blog.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequestDTO));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Return 409 Conflict with empty body
        } catch (Exception e) {
            // Handle other unexpected exceptions (log, return 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticateRequest(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequestDTO));
    }
}
