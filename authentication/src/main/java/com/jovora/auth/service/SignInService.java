package com.jovora.auth.service;

import com.jovora.auth.UserDto;
import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignInRequest;
import com.jovora.auth.repository.UserRepository;
import com.jovora.utils.TextUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public SignInService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public UserDto signIn(SignInRequest signInRequest) {
        User user;

        if (TextUtils.isEmail(signInRequest.username())) {
            user = userRepository.findUserByEmail(signInRequest.username()).orElseThrow(() -> new InvalidUserException("User not found"));
        } else {
            user = userRepository.findUserByUsername(signInRequest.username()).orElseThrow(() -> new InvalidUserException("User not found"));
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.username(), signInRequest.password()));

        return UserDto.builder().identifier(user.getUserId()).username(user.getUsername()).build();
    }
}
