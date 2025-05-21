package com.example.employeemanagement.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.repository.UserImageRepository;

@RestController
@RequestMapping("/user-image")
public class UserImageController {

    @Autowired
    private UserImageRepository userImageRepository;

    @GetMapping("/{username}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable String username) {
        Optional<UserImage> userImageOptional = userImageRepository.findByUsername(username);
        if (userImageOptional.isPresent()) {
            byte[] imageData = userImageOptional.get().getImage();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // or IMAGE_PNG if needed
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
