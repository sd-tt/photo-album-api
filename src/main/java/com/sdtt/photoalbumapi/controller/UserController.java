package com.sdtt.photoalbumapi.controller;

import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
