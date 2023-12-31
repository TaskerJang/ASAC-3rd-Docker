package com.example.dockertutorial.controller;

import com.example.dockertutorial.controller.dto.UserRequestDto;
import com.example.dockertutorial.entity.User;
import com.example.dockertutorial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequestDto request) {
        User user = userRepository.save(request.toEntity());

        return ResponseEntity.created(URI.create("/users/" + user.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(
                userRepository.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                userRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }
}

