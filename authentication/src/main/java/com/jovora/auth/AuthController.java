package com.jovora.auth;

import com.jovora.ErrorDto;
import com.jovora.auth.dto.UserDto;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignInRequest;
import com.jovora.auth.model.SignUpRequest;
import com.jovora.auth.service.SignInService;
import com.jovora.auth.service.SignupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SignupService signupService;

    private final SignInService signInService;

    public AuthController(SignupService signupService, SignInService signInService) {
        this.signupService = signupService;
        this.signInService = signInService;
    }

    @ExceptionHandler({InvalidUserException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request, RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorDto("An error occurred", e.getMessage()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorDto> badCredException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Error", "Invalid username or password"));
    }

    @PostMapping("signup")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(signupService.signup(signUpRequest));
    }

    @PostMapping("signin")
    public ResponseEntity<UserDto> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(signInService.signIn(signInRequest));
    }
}
