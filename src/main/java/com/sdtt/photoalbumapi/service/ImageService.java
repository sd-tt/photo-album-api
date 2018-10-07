package com.sdtt.photoalbumapi.service;

import com.sdtt.photoalbumapi.model.Image;

import java.util.List;

public interface ImageService {

    Image findImageByName(String name);

    List<Image> findAll();
}
