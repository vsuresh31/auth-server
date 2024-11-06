package com.jovora.license;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class License {
    @NotBlank(message = "UserName is mandatory")
    private String identifier;
    @NotBlank(message = "UserName is mandatory")
    private String userName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "PurchaseDate is mandatory")
    @PastOrPresent(message = "PurchaseDate should be past/present date")
    private LocalDate purchaseDate;
    @NotBlank(message = "ExpiryDate is mandatory")
    @Future(message = "ExpiryDate should be future date")
    private LocalDate expiryDate;
}
