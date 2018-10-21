package com.sdtt.photoalbumapi.service;

import com.sdtt.photoalbumapi.model.Image;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface ImageService {

    Image findImageByName(String name);

    List<Image> findAll();

    void upload(MultipartHttpServletRequest request, Long id);
}
