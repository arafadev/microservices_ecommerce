package com.ecommerce.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("GET /api/users");
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest request) {
        log.info("POST /api/users");
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        log.info("GET /api/users/{}", id);
        UserResponse user = userService.fetchUserById(id);
        if (user == null) {
            log.info("User {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User {} found", id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id,
            @RequestBody UserRequest request) {
        log.info("PUT /api/users/{}", id);
        UserResponse user = userService.updateUser(id, request);
        if (user == null) {
            log.info("User {} not found", id);
            return ResponseEntity.notFound().build();
        }
        log.info("User {} updated", id);
        return ResponseEntity.ok(user);
    }
}