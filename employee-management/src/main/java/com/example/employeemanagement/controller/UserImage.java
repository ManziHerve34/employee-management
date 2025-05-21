package com.example.employeemanagement.controller;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UsersImage") // Ensure this matches your MongoDB collection name
public class UserImage {

    @Id
    private String id;
    private String username;
    private byte[] image; // Store the image as binary data

    // Default constructor (required by MongoDB)
    public UserImage() {
    }

    // Parameterized constructor
    public UserImage(String username, byte[] image) {
        this.username = username;
        this.image = image;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}