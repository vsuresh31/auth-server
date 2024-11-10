package com.jovora.auth.model;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(@NotBlank(message = "Username is mandatory") String username,
                            @NotBlank(message = "Password is mandatory") String password) {
}
