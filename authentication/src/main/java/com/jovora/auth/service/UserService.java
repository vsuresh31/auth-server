package com.jovora.auth.service;

import com.jovora.auth.entity.User;
import com.jovora.auth.repository.UserRepository;
import com.jovora.utils.TextUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("Invalid username");
        }

        if (TextUtils.isEmail(username)) {
            return userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }

        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
