package com.sdtt.photoalbumapi.repository;

import com.sdtt.photoalbumapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByHashId(String hashId);
}
