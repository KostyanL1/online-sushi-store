package com.legenkiy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "The product name field cannot be empty!")
    @Size(min = 1, max = 60, message = "The product name must be between 1 and 60 characters long!")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "The product description field cannot be empty!")
    @Size(min = 15, max = 300, message = "The product description must be between 15 and 300 characters long!")
    private String description;

    @NotNull(message = "The product price cannot be null!")
    @Min(value = 0, message = "The product price cannot be negative!")
    @Max(value = 5000, message = "The product price cannot exceed 5000!")
    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "The product type must be specified!")
    private Type type;

    @NotBlank(message = "The image URL field cannot be empty!")
    @URL(message = "Invalid URL format!")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "The product availability must be specified!")
    @Column(name = "is_available")
    private Boolean isAvailable;

}