package com.svc.api.auth.controller;

import com.svc.api.auth.model.vo.AuthVo;
import com.svc.api.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Register and login endpoints")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid AuthVo vo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(vo));
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthVo vo) {
        return ResponseEntity.ok(authService.login(vo));
    }
}
