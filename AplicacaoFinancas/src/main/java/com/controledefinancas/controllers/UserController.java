package com.controledefinancas.controllers;

import com.controledefinancas.models.User;
import com.controledefinancas.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/criar-usuario")
    public ResponseEntity<User> criarUsuario(@RequestParam String nome, @RequestParam double renda) {
        return ResponseEntity.ok(userService.createUser(nome, renda));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<User> getUsuario(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}