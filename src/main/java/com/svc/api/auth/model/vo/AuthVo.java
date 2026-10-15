package com.svc.api.auth.model.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthVo {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid format Email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
