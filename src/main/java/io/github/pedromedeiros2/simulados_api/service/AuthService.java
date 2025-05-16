package io.github.pedromedeiros2.simulados_api.service;

import io.github.pedromedeiros2.simulados_api.config.security.JwtTokenProvider;
import io.github.pedromedeiros2.simulados_api.dto.JwtResponseDTO;
import io.github.pedromedeiros2.simulados_api.dto.LoginRequestDTO;
import io.github.pedromedeiros2.simulados_api.dto.RegisterRequestDTO;
import io.github.pedromedeiros2.simulados_api.model.User;
import io.github.pedromedeiros2.simulados_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public JwtResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return new JwtResponseDTO(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getRole()
        );
    }

    public User registerUser(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!"); // Consider a custom exception
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        return userRepository.save(user);
    }
}
