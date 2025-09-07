package com.example.fishtracking.service;

import com.example.fishtracking.dto.request.LoginRequest;
import com.example.fishtracking.dto.request.RegisterRequest;
import com.example.fishtracking.dto.response.AuthResponse;
import com.example.fishtracking.entity.User;
import com.example.fishtracking.exception.CustomAuthenticationException;
import com.example.fishtracking.exception.ResourceAlreadyExistsException;
import com.example.fishtracking.exception.ResourceNotFoundException;
import com.example.fishtracking.repository.UserRepository;
import com.example.fishtracking.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode( request.getPassword()));

        userService.createUser(user);
        UserDetails userDetails = new UserDetailsImpl(user);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            if (!userRepository.existsByEmail(loginRequest.getEmail())) {
                throw new ResourceNotFoundException("User not found");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new CustomAuthenticationException("Invalid password");
        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException("Authentication failed" + e.getMessage());
        }
    }
}
