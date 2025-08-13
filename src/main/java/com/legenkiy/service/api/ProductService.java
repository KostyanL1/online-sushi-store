package com.legenkiy.service.api;

import com.legenkiy.dto.ProductDto;
import com.legenkiy.model.Product;

import java.util.List;

public interface ProductService {

    Product findById(int id);
    List<Product> findAll();
    void validateExistsById(int id);
    void save(ProductDto productDto);
    void deleteById(int id);
    void update(int id, ProductDto updatedProductDto);

}
