package com.legenkiy.mappers;


import com.legenkiy.dto.ProductDto;
import com.legenkiy.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDto productDto, String imageUrl){
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setProductType(productDto.getProductType());
            product.setImageUrl(imageUrl);
            product.setIsAvailable(productDto.isAvailable());
            return product;
    }

}
