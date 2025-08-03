package com.legenkiy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "promotions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Promotion {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    @NotBlank(message = "The promotion title field cannot be empty!")
    @Size(min = 3, max = 25, message = "The promotion title must be between 3 and 25 characters long!")
    private String title;
    @Column(name = "description")
    @NotBlank(message = "The promotion description field cannot be empty!")
    @Size(min = 15, max = 300, message = "The promotion description must be between 15 and 300 characters long!")
    private String description;
    @Column(name = "image_url")
    @URL(message = "Invalid URL format!")
    private String imageUrl;

}
