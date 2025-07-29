package com.controledefinancas.services;

import com.controledefinancas.models.User;
import com.controledefinancas.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User createUser(String nome, double renda) {
        User user = new User();
        user.setNome(nome);
        user.setRenda(renda);
        user.setSaldo(renda);
        return userRepo.save(user);
    }

    public Optional<User> getUser(Long userId) {
        return userRepo.findById(userId);
    }
}