package org.kane.server.services;

import lombok.RequiredArgsConstructor;
import org.kane.server.DTO.request.LoginRequest;
import org.kane.server.DTO.response.JWTTokenSuccessResponse;
import org.kane.server.security.JWTTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenProvider jwtTokenProvider;

    public JWTTokenSuccessResponse generateToken(LoginRequest loginRequest) {
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtTokenProvider.generateToken(Collections.emptyMap(), userDetails.getUsername());
        return JWTTokenSuccessResponse.builder()
                .token(token)
                .success(true)
                .build();
    }
}

