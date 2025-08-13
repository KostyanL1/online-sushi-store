package com.legenkiy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDto {

    @NotBlank(message = "The description must be present!")
    @Size(min = 1, max = 20 , message = "The length of the product type must not be shorter than 1 or longer than 20!")
    private String description;

}
