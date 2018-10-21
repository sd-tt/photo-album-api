package com.sdtt.photoalbumapi.repository;

import com.sdtt.photoalbumapi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
