package com.jovora.auth.service;

import com.jovora.auth.UserDto;
import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignUpRequest;
import com.jovora.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class SignupService {

    private static final String USER_ALREADY_EXIST = "User already exist";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signup(SignUpRequest signUpRequest) {
        User user = new User();

        if (StringUtils.hasText(signUpRequest.email())) {
            userRepository.findUserByEmail(signUpRequest.email()).ifPresent(d -> {
                throw new InvalidUserException(USER_ALREADY_EXIST);
            });

            user.setUsername(signUpRequest.email());
        } else {
            userRepository.findUserByUsername(signUpRequest.username()).ifPresent(d -> {
                throw new InvalidUserException(USER_ALREADY_EXIST);
            });

            user.setUsername(signUpRequest.username());
        }

        user.setEmail(signUpRequest.email());
        user.setUserId(UUID.randomUUID().toString());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.password()));
        userRepository.save(user);

        return UserDto.builder().username(user.getUsername()).identifier(user.getUserId()).build();
    }
}
