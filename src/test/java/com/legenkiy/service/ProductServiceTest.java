package com.legenkiy.service;

import com.legenkiy.dao.ProductDao;
import com.legenkiy.exceprions.ProductNotFoundException;
import com.legenkiy.mappers.ProductMapper;
import com.legenkiy.model.Product;
import com.legenkiy.model.Type;
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

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CloudService cloudService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldSaveProduct_save_whenProductPresent() {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "image.jpg", "image/jpeg", "test img".getBytes()
        );
        ProductDao productDao = new ProductDao(
                "Sushi", "good", 250.0,
                new Type(1, "Sushi"), multipartFile, true
        );
        Mockito.when(cloudService.upload(Mockito.any(MultipartFile.class), Mockito.anyString()))
                .thenReturn("https://cloudinary.com/sushi.jpg");
        Product product = new Product();
        product.setName("Sushi");
        product.setDescription("good");
        product.setPrice(250.0);
        product.setImageUrl("https://cloudinary.com/sushi.jpg");
        product.setIsAvailable(true);
        product.setType(new Type(1, "Sushi"));
        Mockito.when(productMapper.toEntity(Mockito.any(), Mockito.anyString())).thenReturn(product);
        productService.save(productDao);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        Mockito.verify(productRepository).save(productCaptor.capture());
        Product savedProduct = productCaptor.getValue();
        Assertions.assertEquals("Sushi", savedProduct.getName());
        Assertions.assertEquals("good", savedProduct.getDescription());
        Assertions.assertEquals(250.0, savedProduct.getPrice());
        Assertions.assertEquals("https://cloudinary.com/sushi.jpg", savedProduct.getImageUrl());
        Assertions.assertTrue(savedProduct.getIsAvailable());
    }

    @Test
    void shouldThrow_save_whenProductNotPresent() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.save(null));
    }

    @Test
    void shouldUpdateProduct_save_whenProductExists() {
        int id = 1;
        Product oldProduct = new Product(1, "Sushi", "Good", 250.0,
                new Type(1, "Sushi"), "https://example.com/old-image.jpg", true);
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "new-image.jpg", "image/jpeg", "some-bytes".getBytes()
        );
        ProductDao productDao = new ProductDao(
                "Rolls", "Better", 255.0, new Type(1, "Rolls"),
                mockFile, true
        );
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(oldProduct));
        Mockito.when(cloudService.upload(Mockito.any(), Mockito.any()))
                .thenReturn("https://example.com/new-image.jpg");
        productService.update(id, productDao);
        Mockito.verify(productRepository).save(Mockito.argThat(savedProduct ->
                savedProduct.getName().equals("Rolls") &&
                        savedProduct.getDescription().equals("Better") &&
                        savedProduct.getPrice().equals(255.0) &&
                        savedProduct.getImageUrl().equals("https://example.com/new-image.jpg") &&
                        savedProduct.getType().getDescription().equals("Rolls") &&
                        savedProduct.getIsAvailable()
        ));
    }

    @Test
    void shouldUpdateProduct_keepOldImage_whenNoNewImageProvided() {
        int id = 1;
        Product oldProduct = new Product(1, "Sushi", "Good", 250.0,
                new Type(1, "Sushi"), "https://example.com/old-image.jpg", true);
        ProductDao productDao = new ProductDao(
                "Rolls", "Better", 255.0, new Type(1, "Rolls"),
                null, true
        );
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(oldProduct));
        productService.update(id, productDao);
        Mockito.verify(productRepository).save(Mockito.argThat(savedProduct ->
                savedProduct.getImageUrl().equals("https://example.com/old-image.jpg")
        ));
    }

    @Test
    void shouldThrow_update_whenMissingProduct() {
        ProductDao dummy = new ProductDao("X", "Y", 100.0, new Type(1, "Z"), null, true);
        Mockito.when(productRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.update(0, dummy));
    }

    @Test
    void shouldThrow_update_whenProductIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> productService.update(0, null));
    }

    @Test
    void shouldReturnProduct_findById_whenIdIsCorrect() {
        Product product = new Product(1, "Sushi", "good", 250.0,
                new Type(1, "Sushi"), "https://example.com/placeholder.png", true);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Product result = productService.findById(1);
        Assertions.assertEquals(product, result);
        Mockito.verify(productRepository).findById(1);
    }

    @Test
    void shouldThrow_findById_whenIdNotCorrect() {
        Mockito.when(productRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.findById(0));
    }

    @Test
    void shouldDelete_deleteById_whenIdIsCorrect() {
        Mockito.when(productRepository.existsById(1)).thenReturn(true);
        productService.deleteById(1);
        Mockito.verify(productRepository).deleteById(1);
    }
    @Test
    void shouldThrow_deleteById_whenIdNotCorrect() {
        Mockito.when(productRepository.existsById(1)).thenReturn(false);
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.deleteById(1));
    }

    @Test
    void shouldThrow_validateExistsById_whenProductNonExist() {
        Mockito.when(productRepository.existsById(0)).thenReturn(false);
        Assertions.assertThrows(ProductNotFoundException.class,
                () -> productService.validateExistsById(0));
    }

    @Test
    void shouldReturn_findAll() {
        productService.findAll();
        Mockito.verify(productRepository).findAll();
    }
}
