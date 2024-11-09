package com.jovora.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String username;
    @NotBlank(message = "password is mandatory")
    private String password;
    @Email
    private String email;

}
