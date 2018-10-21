package com.sdtt.photoalbumapi.service;

import com.sdtt.photoalbumapi.model.User;

import java.util.List;

public interface UserService {

    User findByHashId(String hashId);

    User findById(Long id);

    List<User> findAll();
}
