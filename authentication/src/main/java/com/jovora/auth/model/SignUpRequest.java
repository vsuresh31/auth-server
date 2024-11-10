package com.jovora.auth.model;

import com.jovora.validator.GroupValidationNotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@GroupValidationNotBlank(fields = {"username", "email"}, message = "Please provide valid user")
public record SignUpRequest(String username, @Email String email,
                            @NotBlank(message = "Password is mandatory") String password) {
}
