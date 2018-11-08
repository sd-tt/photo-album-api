package com.sdtt.photoalbumapi.service;

import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.payload.UsersPayload;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByUsername(String username);

    List<UsersPayload> findAll();

    void create(User user);
}
