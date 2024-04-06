package com.insights.blog.controller;

import com.insights.blog.payload.LoginRequestDTO;
import com.insights.blog.payload.AuthenticationResponseDTO;
import com.insights.blog.payload.RegisterRequestDTO;
import com.insights.blog.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticateRequest(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
