package com.example.employeemanagement.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.employeemanagement.controller.UserImage; // Correct import
import com.example.employeemanagement.repository.UserImageRepository;

@Service
public class UserImageService {

    @Autowired
    private UserImageRepository userImageRepository;

    public void saveUserImage(String username, MultipartFile file) throws IOException {
        // Convert the image to binary data (byte array)
        byte[] imageData = file.getBytes();

        // Create a new UserImage object
        UserImage userImage = new UserImage(username, imageData);

        // Save the image in MongoDB
        userImageRepository.save(userImage);
        System.out.println("Saved image for user: " + username);
        System.out.println("Total images stored: " + userImageRepository.count());
    }

    // Optionally, you can add a method to retrieve the user image
    public byte[] getUserImage(String username) {
        return userImageRepository.findByUsername(username)
                .map(UserImage::getImage)
                .orElse(null); // Return null if no image is found
    }
}