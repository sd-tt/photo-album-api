package com.sdtt.photoalbumapi.repository;

import com.sdtt.photoalbumapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByHashId(String hashId);
}
