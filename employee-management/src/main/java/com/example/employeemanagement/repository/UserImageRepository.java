package com.example.employeemanagement.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.employeemanagement.controller.UserImage;

public interface UserImageRepository extends MongoRepository<UserImage, String> {
    Optional<UserImage> findByUsername(String username);
}