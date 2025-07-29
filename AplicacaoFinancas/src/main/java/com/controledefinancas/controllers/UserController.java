package com.controledefinancas.controllers;

import com.controledefinancas.models.User;
import com.controledefinancas.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String name, @RequestParam double income) {
        return ResponseEntity.ok(userService.createUser(name, income));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
