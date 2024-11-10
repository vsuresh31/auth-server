package com.jovora.auth.service;

import com.jovora.auth.dto.UserDto;
import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignInRequest;
import com.jovora.auth.repository.UserRepository;
import com.jovora.utils.TextUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTProvider jwtProvider;

    public SignInService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public UserDto signIn(SignInRequest signInRequest) {
        User user;

        if (TextUtils.isEmail(signInRequest.username())) {
            user = userRepository.findUserByEmail(signInRequest.username()).orElseThrow(() -> new InvalidUserException("User not found"));
        } else {
            user = userRepository.findUserByUsername(signInRequest.username()).orElseThrow(() -> new InvalidUserException("User not found"));
        }

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.username(), signInRequest.password()));
        Object details = authenticate.getDetails();

        String token = jwtProvider.generateJWT(user);
        return UserDto.builder().token(token).build();
    }
}
