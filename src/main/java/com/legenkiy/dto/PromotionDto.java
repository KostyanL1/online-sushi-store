package com.legenkiy.dto;

import com.legenkiy.annotations.ImageSize;
import com.legenkiy.annotations.ImageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PromotionDto {
    @NotBlank(message = "The promotion title field cannot be empty!")
    @Size(min = 3, max = 25, message = "The promotion title must be between 3 and 25 characters long!")
    private String title;
    @NotBlank(message = "The promotion description field cannot be empty!")
    @Size(min = 15, max = 300, message = "The promotion description must be between 15 and 300 characters long!")
    private String description;
    @NotNull(message = "The image must be present!")
    @ImageSize(maxSize = 2 * 1024 * 1024, message = "Image size cannot be larger than 2 MB!")
    @ImageType(types = {"image/jpeg", "image/png"}, message = "The image can only be in png or jpeg format!")
    private MultipartFile image;

}
