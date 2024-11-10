package com.jovora.auth;

import com.jovora.ErrorDto;
import com.jovora.auth.entity.User;
import com.jovora.auth.exception.InvalidUserException;
import com.jovora.auth.model.SignInRequest;
import com.jovora.auth.model.SignUpRequest;
import com.jovora.auth.service.SignInService;
import com.jovora.auth.service.SignupService;
import com.jovora.auth.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private SignupService signupService;

    @Autowired
    private SignInService signInService;

    @ExceptionHandler({InvalidUserException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(HttpServletRequest request, RuntimeException e) {
        HttpStatus status = getStatus(request);
        return ResponseEntity.badRequest().body(new ErrorDto("An error occurred", e.getMessage()));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ErrorDto> badCredException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto("Error", "Invalid username or password"));
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.resolve(code);
        return (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
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
    public ResponseEntity<UserDto> registerNewUser(@RequestBody @Valid SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(signupService.signup(signUpRequest));
    }

    @PostMapping("signin")
    public ResponseEntity<UserDto> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseEntity.ok(signInService.signIn(signInRequest));
    }
}
