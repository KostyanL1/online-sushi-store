package com.legenkiy.service;


import com.legenkiy.dto.ProductDto;
import com.legenkiy.exceprions.ImageNotFoundException;
import com.legenkiy.exceprions.ProductNotFoundException;
import com.legenkiy.mappers.ProductMapper;
import com.legenkiy.model.Product;
import com.legenkiy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudService cloudService;
    private final ProductMapper productMapper;

    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id:" + id + " not found!"));
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void validateExistsById(int id){
        if (!productRepository.existsById(id))
            throw new ProductNotFoundException("Product with id:" + id + " doesn`t exist!");
    }


    public void save(ProductDto productDto) {
        if (productDto == null) throw new IllegalArgumentException("Product must not be null!");
        if (productDto.getImage() == null || productDto.getImage().isEmpty()) throw new ImageNotFoundException("Image must be present!");
        String url = cloudService.upload(productDto.getImage(), productDto.getName());
         Product product = productMapper.toEntity(productDto, url);
        productRepository.save(product);
    }

    public void deleteById(int id) throws ProductNotFoundException {
        validateExistsById(id);
        productRepository.deleteById(id);
    }

    public void update(int id, ProductDto updatedProductDto) throws ProductNotFoundException {
        if (updatedProductDto == null) {
            throw new IllegalArgumentException("Product must not be null!");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setProductType(updatedProductDto.getProductType());
        existingProduct.setIsAvailable(updatedProductDto.isAvailable());
        MultipartFile newPhoto = updatedProductDto.getImage();
        if (newPhoto != null && !newPhoto.isEmpty()) {
            String newImageUrl = cloudService.upload(newPhoto, updatedProductDto.getName());
            existingProduct.setImageUrl(newImageUrl);
        }
        productRepository.save(existingProduct);
    }

}
