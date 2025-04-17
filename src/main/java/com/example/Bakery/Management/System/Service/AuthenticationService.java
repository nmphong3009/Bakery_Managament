package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.Component.JwtTokenProvider;
import com.example.Bakery.Management.System.DTOS.Request.LoginRequest;
import com.example.Bakery.Management.System.DTOS.Request.RegisterRequest;
import com.example.Bakery.Management.System.Entity.User;
import com.example.Bakery.Management.System.Enum.Role;
import com.example.Bakery.Management.System.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<?> registerUser(RegisterRequest registerRequest){
        if (userRepository.findByIdCard(registerRequest.getIdCard()).isPresent()) {
            throw new RuntimeException("idCard already exists!");
        }
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        User user = User.builder()
                .fullName(registerRequest.getFullName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .idCard(registerRequest.getIdCard())
                .passWord(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok("Register successful");
    }

    public ResponseEntity<?> login(LoginRequest request){
        User user = userRepository.findByIdCard(request.getIdCard())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idCard: " + request.getIdCard()));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getIdCard(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed");
        }
        String token = jwtTokenProvider.generateToken(user);
        String role = user.getRole().toString();
        return ResponseEntity.ok(Map.of("message", "Login successful", "token", token, "role", role));
    }
}

