package com.graceman.propertymangement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
