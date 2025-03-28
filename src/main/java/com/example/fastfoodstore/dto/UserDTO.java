package com.example.fastfoodstore.dto;

public class UserDTO {
    private Long id;
    private String fullName;

    // Constructors
    public UserDTO() {}
    public UserDTO(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    // Getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}