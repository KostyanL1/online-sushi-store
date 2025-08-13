package com.legenkiy.service.impl;

import com.legenkiy.dto.ProductDto;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.mappers.ProductMapper;
import com.legenkiy.model.Product;
import com.legenkiy.model.ProductType;
import com.legenkiy.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

// should_NameOfMethod_when

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CloudServiceImpl cloudServiceImpl;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Test
    void shouldSaveProduct_save_whenProductPresent() {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "image.jpg", "image/jpeg", "test img".getBytes()
        );
        ProductDto productDto = new ProductDto(
                "Sushi", "good", 250.0,
                new ProductType(1, "Sushi"), multipartFile, true
        );
        String uploadedImageUrl = "https://cloudinary.com/sushi.jpg";
        Mockito.when(cloudServiceImpl.upload(Mockito.any(MultipartFile.class), Mockito.anyString()))
                .thenReturn(uploadedImageUrl);
        Mockito.when(productMapper.toEntity(Mockito.any(), Mockito.eq(uploadedImageUrl)))
                .thenAnswer(invocation -> {
                    ProductDto dto = invocation.getArgument(0);
                    Product product = new Product();
                    product.setName(dto.getName());
                    product.setDescription(dto.getDescription());
                    product.setPrice(dto.getPrice());
                    product.setImageUrl(uploadedImageUrl);
                    product.setIsAvailable(dto.isAvailable());
                    product.setProductType(dto.getProductType());
                    return product;
                });
        productServiceImpl.save(productDto);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        Assertions.assertEquals(productDto.getName(), savedProduct.getName());
        Assertions.assertEquals(productDto.getDescription(), savedProduct.getDescription());
        Assertions.assertEquals(productDto.getPrice(), savedProduct.getPrice());
        Assertions.assertEquals(uploadedImageUrl, savedProduct.getImageUrl());
        Assertions.assertEquals(productDto.isAvailable(), savedProduct.getIsAvailable());
        Assertions.assertEquals(productDto.getProductType(), savedProduct.getProductType());
    }

    @Test
    void shouldThrow_save_whenProductNotPresent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> productServiceImpl.save(null));
    }

    @Test
    void shouldUpdateProduct_update_whenProductExists() {
        int id = 1;
        Product oldProduct = new Product(id, "Sushi", "Good", 250.0,
                new ProductType(1, "Sushi"), "https://example.com/old-image.jpg", true);
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "new-image.jpg", "image/jpeg", "some-bytes".getBytes()
        );
        ProductDto productDto = new ProductDto(
                "Rolls", "Better", 255.0, new ProductType(1, "Rolls"),
                mockFile, true
        );
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(oldProduct));
        Mockito.when(cloudServiceImpl.upload(Mockito.any(), Mockito.any()))
                .thenReturn("https://example.com/new-image.jpg");
        productServiceImpl.update(id, productDto);
        Mockito.verify(productRepository).save(Mockito.argThat(savedProduct ->
                savedProduct.getName().equals("Rolls") &&
                        savedProduct.getDescription().equals("Better") &&
                        savedProduct.getPrice().equals(255.0) &&
                        savedProduct.getImageUrl().equals("https://example.com/new-image.jpg") &&
                        savedProduct.getProductType().getDescription().equals("Rolls") &&
                        savedProduct.getIsAvailable()
        ));
    }

    @Test
    void shouldUpdateProduct_update_whenNoNewImageProvided() {
        int id = 1;
        Product oldProduct = new Product(1, "Sushi", "Good", 250.0,
                new ProductType(1, "Sushi"), "https://example.com/old-image.jpg", true);
        ProductDto productDto = new ProductDto(
                "Rolls", "Better", 255.0, new ProductType(1, "Rolls"),
                null, true
        );
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(oldProduct));
        productServiceImpl.update(id, productDto);
        Mockito.verify(productRepository).save(Mockito.argThat(savedProduct ->
                savedProduct.getImageUrl().equals("https://example.com/old-image.jpg")
        ));
    }

    @Test
    void shouldThrow_update_whenMissingProduct() {
        ProductDto productDto = new ProductDto("X", "Y", 100.0, new ProductType(1, "Z"), null, true);
        Mockito.when(productRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productServiceImpl.update(0, productDto));
    }

    @Test
    void shouldThrow_update_whenProductIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> productServiceImpl.update(0, null));
    }

    @Test
    void shouldReturnProduct_findById_whenIdIsCorrect() {
        Product product = new Product(1, "Sushi", "good", 250.0,
                new ProductType(1, "Sushi"), "https://example.com/placeholder.png", true);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Product result = productServiceImpl.findById(1);
        Assertions.assertEquals(product, result);
        Mockito.verify(productRepository).findById(1);
    }

    @Test
    void shouldThrow_findById_whenIdNotCorrect() {
        Mockito.when(productRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productServiceImpl.findById(0));
    }

    @Test
    void shouldDelete_deleteById_whenIdIsCorrect() {
        Mockito.when(productRepository.existsById(1)).thenReturn(true);
        productServiceImpl.deleteById(1);
        Mockito.verify(productRepository).deleteById(1);
    }

    @Test
    void shouldThrow_validateExistsById_whenProductNonExist() {
        Mockito.when(productRepository.existsById(0)).thenReturn(false);
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productServiceImpl.validateExistsById(0));
    }

    @Test
    void shouldReturn_findAll() {
        productServiceImpl.findAll();
        Mockito.verify(productRepository).findAll();
    }
}
