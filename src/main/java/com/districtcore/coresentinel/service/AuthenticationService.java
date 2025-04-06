package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.user.LoginUserDto.LoginUserRequestDto;
import com.districtcore.coresentinel.dto.user.SignupUserDto.SignupUserRequestDto;
import com.districtcore.coresentinel.mapper.UserMapper;
import com.districtcore.coresentinel.model.User;
import com.districtcore.coresentinel.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User signup(SignupUserRequestDto userDto) {
        User user;

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = this.userMapper.signupUserRequestToEntity(userDto);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserRequestDto user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        return userRepository.findByUsername(user.getUsername())
                .orElseThrow();
    }
}
