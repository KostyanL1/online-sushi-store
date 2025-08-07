package com.legenkiy.validation;


import com.legenkiy.annotations.ImageSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageSizeValidator implements ConstraintValidator<ImageSize, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(ImageSize constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()){
            return true;
        }
        return multipartFile.getSize() <= maxSize;
    }
}
