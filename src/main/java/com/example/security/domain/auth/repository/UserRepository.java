package com.example.security.domain.auth.repository;

import com.example.security.domain.auth.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query(value = "select u "
        + "from User u "
        + "where u.userId = :userId")
    Optional<User> findUserByUserId(String userId);
}
