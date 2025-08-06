package com.legenkiy.validation;

import com.legenkiy.annotations.ImageType;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageTypeValidator implements ConstraintValidator<ImageType, MultipartFile> {

    private List<String> types;

    @Override
    public void initialize(ImageType constraintAnnotation) {
        this.types = Arrays.asList(constraintAnnotation.types());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return true;
        }
        return types.contains(multipartFile.getContentType());
    }
}
