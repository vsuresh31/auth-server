package com.jovora.auth;

import com.jovora.ErrorDto;
import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignUpRequest;
import com.jovora.auth.repository.UserRepository;
import com.jovora.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @ExceptionHandler({InvalidUserException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorDto("An error occurred", e.getMessage()));

    }

//    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping
    public UserDto test2() {
        User user = userService.loadUserByUsername("sv_test1");
        return new UserDto.UserDtoBuilder().identifier(user.getUserId()).username(null != user.getUsername() ? user.getUsername() : user.getEmail()).build();
    }

    @PostMapping("signup")
    public User registerNewUser(@RequestBody SignUpRequest signUpRequest) {
        return userService.signUpUser(signUpRequest);

    }

}
