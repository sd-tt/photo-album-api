package com.sdtt.photoalbumapi.controller;

import com.sdtt.photoalbumapi.model.Image;
import com.sdtt.photoalbumapi.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api")
public class ImageController {

    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService service;

    @GetMapping("image/{name}")
    public ResponseEntity<Image> findImageByName(@PathVariable("name") String name)
            throws EntityNotFoundException {
        Image image = service.findImageByName(name);
        return ResponseEntity.status(image.isEmpty() ? 404 : 200).body(image);
    }

    @GetMapping("images")
    public ResponseEntity<List<Image>> getImages() {
        List<Image> images = service.findAll();
        return ResponseEntity.ok(images);
    }

    @PostMapping(path = "upload/{id}")
    public ResponseEntity<Object> upload(MultipartHttpServletRequest request, @PathVariable("id") Long id) {
        service.upload(request, id);
        return ResponseEntity.ok().build();
    }
}
