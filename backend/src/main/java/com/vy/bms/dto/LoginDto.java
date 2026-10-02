package com.vy.bms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}