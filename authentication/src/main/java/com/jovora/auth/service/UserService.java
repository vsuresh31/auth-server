package com.jovora.auth.service;

import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignUpRequest;
import com.jovora.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class UserService implements UserDetailsService {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("Invalid username");
        }

        return userRepository.findUserByEmailOrUsername(username, username);
    }

    public User signUpUser(SignUpRequest signUpRequest) {
        if (!StringUtils.hasText(signUpRequest.getEmail()) && !StringUtils.hasText(signUpRequest.getUsername())) {
            throw new InvalidUserException("Invalid username");
        }

        User user = new User();

        if (StringUtils.hasText(signUpRequest.getUsername())) {
            if (null != userRepository.findUserByUsername(signUpRequest.getUsername())) {
                throw new InvalidUserException("User already exists");
            }
            user.setUsername(signUpRequest.getUsername());
        } else {
            if (null != userRepository.findUserByEmail(signUpRequest.getEmail())) {
                throw new InvalidUserException("User already exists");
            }
            user.setEmail(signUpRequest.getEmail());
        }

        user.setUserId(UUID.randomUUID().toString());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }
}
