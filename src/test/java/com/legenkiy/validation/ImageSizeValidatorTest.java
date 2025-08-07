package com.legenkiy.validation;


import com.legenkiy.annotations.ImageSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

// should_NameOfMethod_when

public class ImageSizeValidatorTest {

    private  ImageSizeValidator imageSizeValidator;


    @BeforeEach
  void setUp(){
        imageSizeValidator = new ImageSizeValidator();
        ImageSize annotation = Mockito.mock(ImageSize.class);
        Mockito.when(annotation.maxSize()).thenReturn((long) (2 * 1024 * 1024));
        imageSizeValidator.initialize(annotation);
    }


    @Test
    void shouldReturnTrue_isValid_whenSizeIsCorrect(){
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test png".getBytes()
        );
        Assertions.assertTrue(imageSizeValidator.isValid(multipartFile, null));
    }
    @Test
    void shouldReturnFalse_isValid_whenSizeIsCorrect(){
        MultipartFile multipartFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", new byte[3 * 1024 * 1024]
        );
        Assertions.assertFalse(imageSizeValidator.isValid(multipartFile, null));
    }
    @Test
    void shouldReturnTrue_isValid_whenFileIsNull(){
        Assertions.assertTrue(imageSizeValidator.isValid(null, null));
    }

}
