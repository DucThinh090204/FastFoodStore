package com.example.fastfoodstore.dto;

public class MenuItemDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String image;
    private Long categoryId;

    // Constructors
    public MenuItemDTO() {}
    public MenuItemDTO(Long id, String name, double price, String description, String image, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.categoryId = categoryId;
    }

    // Getters, setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}