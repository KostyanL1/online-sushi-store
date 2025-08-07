package com.legenkiy.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.legenkiy.exceptions.CloudUploadException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// should_NameOfMethod_when

@ExtendWith(MockitoExtension.class)
public class CloudServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @InjectMocks
    private CloudService cloudService;

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(cloudService, "cloudinary", cloudinary);
    }



    @Test
    public void shouldUpload_upload_whenFileIsPresent() throws IOException {
        Mockito.when(cloudinary.uploader()).thenReturn(uploader);
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fake content".getBytes()
        );
        Map<String, Object> mockResult = new HashMap<>();
        mockResult.put("secure_url", "test url");
        Mockito.when(uploader.upload(Mockito.any(byte[].class), Mockito.anyMap())).thenReturn(mockResult);
        String url = cloudService.upload(multipartFile, "test");
        Assertions.assertEquals("test url", url);
    }

    @Test
    public void shouldThrow_upload_whenFileNotCorrect(){
        Mockito.when(cloudinary.uploader()).thenReturn(uploader);
        MockMultipartFile multipartFile = null;
        Assertions.assertThrows(CloudUploadException.class, () -> cloudService.upload(multipartFile, "test"));
    }


}
