package com.sdtt.photoalbumapi.service.impl;

import com.sdtt.photoalbumapi.model.Image;
import com.sdtt.photoalbumapi.model.User;
import com.sdtt.photoalbumapi.repository.ImageRepository;
import com.sdtt.photoalbumapi.service.ImageService;
import com.sdtt.photoalbumapi.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${images.location}")
    private String imagesLocation;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ResourceLoader loader;

    @Autowired
    private AccessToken token;

    @Override
    public Image findImageByName(String name) {
        User user = userService.findByHashId(token.getSubject());
        return user.getImages().stream()
                .filter(image -> image.getName().equals(name))
                .map(this::setBase64Data)
                .findFirst()
                .orElseGet(Image::new);
    }

    @Override
    public List<Image> findAll() {
        User user = userService.findByHashId(token.getSubject());
        return user.getImages().stream()
                .map(this::setBase64Data)
                .collect(Collectors.toList());
    }

    @Override
    public void upload(MultipartHttpServletRequest request, Long id) {
        Iterator<String> files = request.getFileNames();
        User user = userService.findById(id);
        while (files.hasNext()) {
            MultipartFile file = request.getFile(files.next());
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                Path fileUrl = Paths.get(imagesLocation, user.getHashId(), originalFilename);
                try {
                    byte[] bytes = file.getBytes();
                    Image image = Image.builder()
                            .setFullName(originalFilename)
                            .setExtension(FilenameUtils.getExtension(originalFilename))
                            .setName(FilenameUtils.getBaseName(originalFilename))
                            .setUrl(fileUrl.toString())
                            .setUser(user)
                            .setLength(bytes.length)
                            .setBase64Data(Base64.encodeBase64String(bytes))
                            .build();
                    if (!fileUrl.toFile().exists()) {
                        FileUtils.writeByteArrayToFile(new File(fileUrl.toUri()), bytes);
                        imageRepository.save(image);
                        LOG.info("{} has been uploaded to the server", image);
                    }
                } catch (IOException exception) {
                    LOG.error("Error during uploading an image", exception);
                }
            }
        }
    }

    private Image setBase64Data(Image image) {
        try {
            byte[] bytes = FileUtils.readFileToByteArray(new File(image.getUrl()));
            image.setBase64Data(Base64.encodeBase64String(bytes));
            image.setLength(bytes.length);
            return image;
        } catch (IOException exception) {
            LOG.error("Error during loading image {}", image.getFullName());
            return new Image();
        }
    }
}
