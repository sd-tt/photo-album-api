package com.sdtt.photoalbumapi.service.impl;

import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.repository.UserRepository;
import com.sdtt.photoalbumapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByHashId(String hashId) {
        return userRepository.findByHashId(hashId).orElseThrow(() ->
                new EntityNotFoundException("User with hash id {" + hashId + "} doesn't exist"));
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id {" + id + "} doesn't exist"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
