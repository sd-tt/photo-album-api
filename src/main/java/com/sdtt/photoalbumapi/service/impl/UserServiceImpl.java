package com.sdtt.photoalbumapi.service.impl;

import com.google.common.hash.Hashing;
import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.payload.UsersPayload;
import com.sdtt.photoalbumapi.repository.UserRepository;
import com.sdtt.photoalbumapi.service.UserService;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.representations.account.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${images.location}")
    private String imagesLocation;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String url;

    @Autowired
    private KeycloakRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id {" + id + "} doesn't exist"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User {" + username + "} doesn't exist"));
    }

    @Override
    public List<UsersPayload> findAll() {
        return userRepository.findAll().stream().map(UsersPayload::new).collect(Collectors.toList());
    }

    @Override
    public void create(User user) {
        user.setUsername((user.getFirstName() + user.getLastName()).toLowerCase());
        user.setUsernameHash(Hashing.sha256().hashString(user.getUsername(), UTF_8).toString());
        if (!userRepository.findByUsernameHash(user.getUsernameHash()).isPresent()) {
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());
            userRepresentation.setUsername(user.getUsername());
            createUserDirectory(user);
            User savedUser = userRepository.save(user);
            if (savedUser.getId() != null) {
                restTemplate.postForObject(url + "/admin/realms/" + realm + "/users", userRepresentation, UserRepresentation.class);
            }
        }
    }

    private void createUserDirectory(User user) {
        File file = Paths.get(imagesLocation, user.getUsernameHash()).toFile();
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
