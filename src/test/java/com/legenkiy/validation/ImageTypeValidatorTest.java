package com.legenkiy.validation;


import com.legenkiy.annotations.ImageType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class ImageTypeValidatorTest {

    private ImageTypeValidator imageTypeValidator;


    @BeforeEach
    void setUp(){
        imageTypeValidator = new ImageTypeValidator();
        ImageType imageType = Mockito.mock(ImageType.class);
        Mockito.when(imageType.types()).thenReturn(new String[]{"image/png"});
        imageTypeValidator.initialize(imageType);
    }


    @Test
    void shouldReturnTrue_isValid_whenTypeIsCorrect(){
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", "image/png", new byte[100]
        );
        Assertions.assertTrue(imageTypeValidator.isValid(multipartFile, null));
    }
    @Test
    void shouldReturnTrue_isValid_whenTypeNotCorrect(){
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "file.png", "image/jpeg", new byte[100]
        );
        Assertions.assertFalse(imageTypeValidator.isValid(multipartFile, null));
    }
    @Test
    void shouldReturnTrue_isValid_whenImageNotPresent(){
        Assertions.assertTrue(imageTypeValidator.isValid(null, null));
    }
}
