package com.legenkiy.dao;

import com.legenkiy.model.Type;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDao {
    @NotBlank(message = "Product name must not be empty!")
    @Size(min = 3, max = 25, message = "Product name must be between 3 and 25 characters long!")
    private String name;

    @NotBlank(message = "Product description must not be empty!")
    @Size(min = 15, max = 300, message = "Product description must be between 15 and 300 characters long!")
    private String description;

    @NotNull(message = "Product price must not be null!")
    @Min(value = 0, message = "Product price cannot be negative!")
    @Max(value = 5000, message = "Product price cannot exceed 5000!")
    private Double price;

    @NotNull(message = "Product type must be specified!")
    private Type type;

    @NotBlank(message = "Product image file must be provided!")
    private MultipartFile image;

    @NotNull(message = "Product availability must be indicated!")
    private boolean available;

}
