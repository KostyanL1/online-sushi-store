package com.legenkiy.annotations;



import com.legenkiy.validation.ImageTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageTypeValidator.class)

public @interface ImageType {

    String message() default "Wrong image format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] types() default {"image/jpeg"};

 }
