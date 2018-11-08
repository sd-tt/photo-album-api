package com.sdtt.photoalbumapi.controller;

import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.payload.UsersPayload;
import com.sdtt.photoalbumapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/{id}")
    public ResponseEntity<User> findById(@PathVariable("name") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @CrossOrigin
    @GetMapping("users")
    public ResponseEntity<List<UsersPayload>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("users")
    public ResponseEntity<Void> create(@RequestBody User user) {
        userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
