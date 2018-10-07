package com.sdtt.photoalbumapi.service.impl;

import com.sdtt.photoalbumapi.model.Image;
import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.repository.UserRepository;
import com.sdtt.photoalbumapi.service.ImageService;
import org.apache.commons.codec.binary.Base64;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private AccessToken token;

    @Override
    public Image findImageByName(String name) {
        User user = repository.findByHashId(token.getSubject());
        return user.getImages().stream()
                .filter(image -> image.getName().equals(name))
                .map(this::setBase64Data)
                .findFirst()
                .orElseGet(Image::new);
    }

    @Override
    public List<Image> findAll() {
        User user = repository.findByHashId(token.getSubject());
        return user.getImages().stream()
                .map(this::setBase64Data)
                .collect(Collectors.toList());
    }

    private Image setBase64Data(Image image) {
        try {
            Resource resource = loader.getResource("classpath:" + image.getUrl());
            byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
            image.setBase64Data(Base64.encodeBase64String(bytes));
            image.setLength(bytes.length);
            return image;
        } catch (IOException exception) {
            LOG.error("Error during loading image {}", image.getFullName());
            return new Image();
        }
    }
}
