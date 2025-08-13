package com.legenkiy.service.impl;


import com.legenkiy.dto.ProductDto;
import com.legenkiy.exceptions.ImageNotFoundException;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.mappers.ProductMapper;
import com.legenkiy.model.Product;
import com.legenkiy.repository.ProductRepository;
import com.legenkiy.service.api.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CloudServiceImpl cloudServiceImpl;
    private final ProductMapper productMapper;

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Product with id:" + id + " not found!"));
    }

    @Override
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public void validateExistsById(int id){
        if (!productRepository.existsById(id))
            throw new ObjectNotFoundException("Product with id:" + id + " doesn`t exist!");
    }

    @Override
    public void save(ProductDto productDto) {
        if (productDto == null) throw new IllegalArgumentException("Product must not be null!");
        if (productDto.getImage() == null || productDto.getImage().isEmpty()) throw new ImageNotFoundException("Image must be present!");
        String url = cloudServiceImpl.upload(productDto.getImage(), productDto.getName());
        Product product = productMapper.toEntity(productDto, url);
        productRepository.save(product);
    }
    @Override
    public void deleteById(int id) throws ObjectNotFoundException {
        validateExistsById(id);
        productRepository.deleteById(id);
    }

    @Override
    public void update(int id, ProductDto updatedProductDto) throws ObjectNotFoundException {
        if (updatedProductDto == null) {
            throw new IllegalArgumentException("Product must not be null!");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product with id " + id + " not found"));
        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setProductType(updatedProductDto.getProductType());
        existingProduct.setIsAvailable(updatedProductDto.isAvailable());
        MultipartFile newPhoto = updatedProductDto.getImage();
        if (newPhoto != null && !newPhoto.isEmpty()) {
            String newImageUrl = cloudServiceImpl.upload(newPhoto, updatedProductDto.getName());
            existingProduct.setImageUrl(newImageUrl);
        }
        productRepository.save(existingProduct);
    }

}
