package com.base.recruitment.controller;

import com.base.recruitment.dto.auth.AuthRequest;
import com.base.recruitment.dto.auth.AuthResponse;
import com.base.recruitment.service.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch(BadCredentialsException e) {
            log.error("ERROR: {}", e.getMessage());
            throw new Exception("Error al autenticar");
        }
        UserDetails details = userDetailsService.loadUserByUsername(request.username());
        String jwt = tokenService.generateToken(details);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
