package com.svc.api.auth.service.impl;

import com.svc.api.auth.config.exception.GlobalExceptionHandler;
import com.svc.api.auth.model.entity.Users;
import com.svc.api.auth.model.vo.AuthVo;
import com.svc.api.auth.repository.AuthRepository;
import com.svc.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(AuthVo vo) {
        if (repository.existsByEmail(vo.getEmail())) {
            throw new GlobalExceptionHandler.ConflictException("Email already registered");
        }

        String hashed = passwordEncoder.encode(vo.getEmail());
        repository.save(vo.getEmail(), hashed)
                .orElseThrow(() -> new GlobalExceptionHandler.ConflictException("Username already taken"));

        return "User registered successfully";
    }

    @Override
    public String login(AuthVo vo) {
        Users user = repository.findByUsername(vo.getEmail())
                .orElseThrow(() -> new GlobalExceptionHandler.UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(vo.getEmail(), user.getPassword())) {
            throw new GlobalExceptionHandler.UnauthorizedException("Invalid username or password");
        }

        return "Login successful";
    }
}
