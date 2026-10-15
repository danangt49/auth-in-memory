package com.svc.api.auth.repository;

import com.svc.api.auth.model.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AuthRepository {
    private final Map<String, Users> usersByEmail = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Optional<Users> findByUsername(String email) {
        return Optional.ofNullable(usersByEmail.get(email.toLowerCase()));
    }

    public boolean existsByEmail(String email) {
        return usersByEmail.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public Optional<Users> save(String email, String hashedPassword) {
        Users user = new Users(idGenerator.getAndIncrement(), email, hashedPassword);
        Users existing = usersByEmail.putIfAbsent(email, user);
        return existing == null ? Optional.of(user) : Optional.empty();
    }
}
